package ru.gribnoff.springshop.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gribnoff.springshop.persistence.entities.Review;
import ru.gribnoff.springshop.services.db.ProductService;
import ru.gribnoff.springshop.services.db.ReviewService;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Api("Admin actions")
public class AdminController {
    private final ProductService productService;
    private final ReviewService reviewService;
    private static List<Review> reviewsToApprove;

    @GetMapping
    @ApiIgnore
    public String adminPage(Model model, Principal principal) {

        if (principal == null)
            return "redirect:/";

        reviewsToApprove = reviewService.getUnapprovedReviews().orElse(new ArrayList<>());

        model.addAttribute("products", productService.findAll());
        model.addAttribute("reviewsToApprove", reviewsToApprove);
        return "admin";
    }

    @GetMapping("/add_product")
    @ApiOperation(
            value = "add new product page",
            notes = "page with interface for adding new product to shop")
    public String addProductPage() {
        return "addproduct";
    }

    @GetMapping("/reviews")
    @ApiOperation("страница новых отзывов")
    public String reviewsPage(Model model) {
        model.addAttribute("reviewsToApprove", reviewsToApprove);
        return "adminreviews";
    }

    @GetMapping("/reviews/remove/{id}")
    @ApiOperation("удаление отзыва по id")
    public String removeReview(@PathVariable UUID id) {
        reviewsToApprove.removeIf(review -> review.getId().equals(id));
        reviewService.removeReview(reviewService.getReviewById(id).get());
        return "redirect:/admin/reviews";
    }

    @GetMapping("/reviews/approve/{id}")
    @ApiOperation("одобрение отзыва по id")
    public String approveReview(@PathVariable UUID id) {
        reviewsToApprove.removeIf(review -> review.getId().equals(id));
        Review review = reviewService.getReviewById(id).get();
        review.setApproved(true);
        reviewService.saveReview(review);
        return "redirect:/admin/reviews";
    }
}
