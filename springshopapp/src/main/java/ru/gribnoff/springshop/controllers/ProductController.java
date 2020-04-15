package ru.gribnoff.springshop.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gribnoff.springshop.exceptions.NotFoundException;
import ru.gribnoff.springshop.exceptions.WrongCaptchaCodeException;
import ru.gribnoff.springshop.persistence.entities.Image;
import ru.gribnoff.springshop.persistence.entities.Product;
import ru.gribnoff.springshop.persistence.entities.Review;
import ru.gribnoff.springshop.persistence.entities.ShopUser;
import ru.gribnoff.springshop.persistence.entities.enums.ImageCategory;
import ru.gribnoff.springshop.persistence.entities.enums.Role;
import ru.gribnoff.springshop.persistence.pojo.ProductPojo;
import ru.gribnoff.springshop.persistence.pojo.ReviewPojo;
import ru.gribnoff.springshop.services.db.ImageService;
import ru.gribnoff.springshop.services.db.ProductService;
import ru.gribnoff.springshop.services.db.ReviewService;
import ru.gribnoff.springshop.services.db.ShopUserService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
@Api("действия с товарами")
public class ProductController {
    private final AmqpTemplate amqpTemplate;
    private final ImageService imageService;
    private final ProductService productService;
    private final ShopUserService shopUserService;
    private final ReviewService reviewService;

    @SuppressWarnings("unused")
    @GetMapping("/{id}")
    @ApiOperation("страница определённого товара")
    public String showProductPage(Model model, @PathVariable String id) throws NotFoundException {
        Product product = productService.findOneById(UUID.fromString(id));
        List<Review> reviews = reviewService.getReviewsByProduct(product).orElse(new ArrayList<>());

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "product";
    }

    @SuppressWarnings("unused")
    @ResponseBody
    @GetMapping(value = "/images/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ApiOperation("получение фото товара по id изображения")
    public byte[] getProductImageByImageId(@PathVariable String id) throws IOException {
        return getImage(id, ImageCategory.PRODUCT_IMAGE);
    }

    @ResponseBody
    @GetMapping(value = "/icons/{id:.+}", produces = MediaType.IMAGE_PNG_VALUE)
    @ApiOperation("получение иконки по id изображения")
    public byte[] getIconByName(@PathVariable String id) throws IOException {
        return getImage(id, ImageCategory.ICON);
    }

    @ResponseBody
    @GetMapping(value = "/reviews/images/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ApiOperation("получение фото отзыва по id изображения")
    public byte[] getReviewPhotoByImageId(@PathVariable String id) throws IOException {
        return getImage(id, ImageCategory.REVIEW_PHOTO);
    }

    @ResponseBody
    private byte[] getImage(String id, ImageCategory category) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = imageService.loadFileAsResource(id, category);
        if (bufferedImage != null) {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } else {
            return new byte[0];
        }
    }

    @PostMapping
    @SuppressWarnings("SpringMVCViewInspection")
    @ApiOperation("добавление товара а базу")
    public String addProductToDatabase(@RequestParam("image") MultipartFile image, ProductPojo productPojo) throws IOException {
        Image img = imageService.uploadImage(image, ImageCategory.PRODUCT_IMAGE);
        productService.save(productPojo, new ArrayList<>(Collections.singletonList(img)));
        return "redirect:/admin/";
    }

    @PostMapping("/reviews")
    @ApiOperation(value = "добавление отзыва", response = String.class, httpMethod = "POST")
    public String addReview(ReviewPojo reviewPojo, @RequestParam(value = "image", required = false) MultipartFile image, HttpSession session, Principal principal) throws NotFoundException, IOException {
        if (reviewPojo.getCaptchaCode().toUpperCase().equals(session.getAttribute("captchaCode"))) {
            Product product = productService.findOneById(reviewPojo.getProductId());
            ShopUser shopUser = shopUserService.findShopUserByPhone(principal.getName());
            boolean approved = shopUser.getRole().equals(Role.ROLE_ADMIN);
            Image img = !image.isEmpty() ?
                    imageService.uploadReviewPhoto(image, product, shopUser) :
                    null;
            Review review = Review.builder()
                    .shopUser(shopUser)
                    .product(product)
                    .comment(reviewPojo.getComment())
                    .image(img)
                    .approved(approved)
                    .build();

            amqpTemplate.convertAndSend("spring-shop.exchange", "spring.shop", "User " + shopUser.getPhone() + " has sent a review");

            reviewService.saveReview(review);
            return "redirect:/products/" + product.getId();
        } else
            throw new WrongCaptchaCodeException("Wrong captcha");
    }
}
