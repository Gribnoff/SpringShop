package ru.gribnoff.springshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gribnoff.springshop.services.ProductService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final ProductService productService;

    @GetMapping("/admin")
    public String adminPage(Model model, @CookieValue(value = "data", required = false) String data, Principal principal) {

        if (principal == null)
            return "redirect:/";

        if (data != null)
            System.out.println(data);

        model.addAttribute("products", productService.findAll(null));
        return "admin";
    }

    @GetMapping("/admin/add_product")
    public String addProductPage() {
        return "addproduct";
    }
}
