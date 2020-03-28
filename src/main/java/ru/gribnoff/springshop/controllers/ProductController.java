package ru.gribnoff.springshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gribnoff.springshop.exceptions.ProductNotFoundException;
import ru.gribnoff.springshop.persistence.entities.Image;
import ru.gribnoff.springshop.persistence.entities.Product;
import ru.gribnoff.springshop.persistence.entities.Review;
import ru.gribnoff.springshop.persistence.pojo.ProductPojo;
import ru.gribnoff.springshop.persistence.pojo.ReviewPojo;
import ru.gribnoff.springshop.services.ImageService;
import ru.gribnoff.springshop.services.ProductService;
import ru.gribnoff.springshop.services.ReviewService;
import ru.gribnoff.springshop.services.ShopUserService;

import javax.imageio.ImageIO;

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
public class ProductController {

    private final ImageService imageService;
    private final ProductService productService;
    private final ShopUserService shopUserService;
    private final ReviewService reviewService;

    @SuppressWarnings("unused")
    @GetMapping("/{id}")
    public String showProductPage(Model model, @PathVariable String id) throws ProductNotFoundException {
        Product product = productService.findOneById(UUID.fromString(id));
        List<Review> reviews = reviewService.getReviewsByProduct(product).orElse(new ArrayList<>());

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "product";
    }

    @SuppressWarnings("unused")
    @ResponseBody
    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImageById(@PathVariable String id) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = imageService.loadFileAsResource(id);
        if (bufferedImage != null) {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } else {
            return new byte[0];
        }
    }

    @PostMapping
    public String addProductToDatabase(@RequestParam("image") MultipartFile image, ProductPojo productPojo) throws IOException {
        Image img = imageService.uploadImage(image);
        return productService.save(productPojo, new ArrayList<>(Collections.singletonList(img)));
    }

    @PostMapping("/reviews")
    public String addReview(ReviewPojo reviewPojo, Principal principal) throws ProductNotFoundException {
        Product product = productService.findOneById(reviewPojo.getProduct());
        Review review = Review.builder()
                .comment(reviewPojo.getComment())
                .product(product)
                .shopUser(shopUserService.findShopUserByPhone(principal.getName()))
                .build();

        reviewService.save(review);
        return "redirect:/products/" +product.getId();
    }
}
