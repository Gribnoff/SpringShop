package ru.gribnoff.springshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.gribnoff.springshop.beans.Cart;
import ru.gribnoff.springshop.services.ProductService;
import ru.gribnoff.springshop.services.ShopUserService;
import ru.gribnoff.springshop.util.CaptchaGenerator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final Cart cart;
    private final ProductService productService;
    private final ShopUserService shopUserService;
    private final CaptchaGenerator captchaGenerator;

    @SuppressWarnings("unused")
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index(Model model, @RequestParam(required = false) Integer category) {
        model.addAttribute("cart", cart.getCartRecords());
        model.addAttribute("products", productService.findAll(category));
        return "index";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        if (principal == null)
            return "redirect:/";

        model.addAttribute("shopUser", shopUserService.findShopUserByPhone(principal.getName()));

        return "profile";
    }

    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getCaptcha(HttpSession session) throws IOException {
        BufferedImage bufferedImage = captchaGenerator.getCaptchaImage();
        session.setAttribute("captchaCode", captchaGenerator.getCaptchaString());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", out);

        return out.toByteArray();
    }
}
