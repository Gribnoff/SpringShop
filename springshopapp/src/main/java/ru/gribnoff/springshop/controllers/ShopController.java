package ru.gribnoff.springshop.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.gribnoff.paymentservice.Payment;
import ru.gribnoff.springshop.beans.Cart;
import ru.gribnoff.springshop.services.db.ProductService;
import ru.gribnoff.springshop.services.db.ShopUserService;
import ru.gribnoff.springshop.services.feign.clients.ShopFeignClient;
import ru.gribnoff.springshop.util.CaptchaGenerator;
import ru.gribnoff.springshop.util.PaymentServiceUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Api("методы общего назначения")
public class ShopController {

    private final Cart cart;
    private final CaptchaGenerator captchaGenerator;

    private final ProductService productService;
    private final ShopUserService shopUserService;
    private final ShopFeignClient shopFeignClient;

    @SuppressWarnings("unused")
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ApiOperation("главная страница")
    public String index(Model model,
                        @RequestParam(required = false) Integer category,
                        @RequestParam(required = false) Integer minPrice,
                        @RequestParam(required = false) Integer maxPrice,
                        @RequestParam(required = false) Boolean notAvailable
                        ) {
        model.addAttribute("cart", cart.getCartRecords());
        model.addAttribute("products", productService.findAll(category, minPrice, maxPrice, notAvailable));
        return "index";
    }

    @GetMapping("/profile")
    @ApiOperation("личный кабинет")
    public String profilePage(Model model, Principal principal) {
        if (principal == null)
            return "redirect:/";

        model.addAttribute("shopUser", shopUserService.findShopUserByPhone(principal.getName()));

        return "profile";
    }

    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    @ApiOperation("генерация капчи")
    public byte[] getCaptcha(HttpSession session) throws IOException {
        BufferedImage bufferedImage = captchaGenerator.getCaptchaImage();
        session.setAttribute("captchaCode", captchaGenerator.getCaptchaString());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", out);

        return out.toByteArray();
    }

    @PostMapping("/checkout")
    @ApiOperation("подтверждение заказа")
    public String proceedToCheckout(Model model, String paymentId) {
        Payment payment = cart.getPaymentSystems()
                .stream()
                .filter(p -> p.getId() == Integer.parseInt(paymentId))
                .collect(PaymentServiceUtil.toSingleton());
        cart.setPayment(payment);

        model.addAttribute("cart", cart);
        return "checkout";
    }

    @GetMapping("/flyer")
    @ApiOperation("feign client demonstration")
    public ResponseEntity<byte[]> getFlyer() {
        return shopFeignClient.getFlyer();
    }
}
