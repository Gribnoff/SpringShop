package ru.gribnoff.springshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gribnoff.springshop.beans.Cart;
import ru.gribnoff.springshop.exceptions.ProductNotFoundException;
import ru.gribnoff.springshop.services.db.ProductService;
import ru.gribnoff.springshop.services.soap.PaymentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController  {

    private final Cart cart;
    private final ProductService productService;
    private final PaymentService paymentService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("payments", paymentService.getPayment("Russia"));
        return "cart";
    }

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable UUID id, HttpServletRequest request, HttpServletResponse response) throws ProductNotFoundException, IOException {
        cart.add(productService.findOneById(id));
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/remove/{id}")
    public String removeProductFromCart(@PathVariable UUID id) {
        cart.removeByProductId(id);
        return "redirect:/cart";
    }
}
