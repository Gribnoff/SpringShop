package ru.gribnoff.springshop.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("действия с корзиной покупок")
public class CartController  {

    private final Cart cart;
    private final ProductService productService;

    @GetMapping
    @ApiOperation("страница корзины")
    public String showCart(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/add/{id}")
    @ApiOperation("добавление товара в корзину")
    public void addProductToCart(@PathVariable UUID id, HttpServletRequest request, HttpServletResponse response) throws ProductNotFoundException, IOException {
        cart.add(productService.findOneById(id));
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/remove/{id}")
    @ApiOperation("удаление товара из корзины")
    public String removeProductFromCart(@PathVariable UUID id) {
        cart.removeByProductId(id);
        return "redirect:/cart";
    }
}
