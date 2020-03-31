package ru.gribnoff.springshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gribnoff.springshop.persistence.entities.Review;
import ru.gribnoff.springshop.services.db.ProductService;
import ru.gribnoff.springshop.services.db.ReviewService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ProductService productService;
    private final ReviewService reviewService;
    private static List<Review> reviewsToApprove;

    @GetMapping
    public String adminPage(Model model, @CookieValue(value = "data", required = false) String data, Principal principal) {

        if (principal == null)
            return "redirect:/";

        if (data != null)
            System.out.println(data);

        reviewsToApprove = reviewService.getUnapprovedReviews().orElse(new ArrayList<>());

        model.addAttribute("products", productService.findAll(null));
        model.addAttribute("reviewsToApprove", reviewsToApprove);
        return "admin";
    }

    @GetMapping("/add_product")
    public String addProductPage() {
        return "addproduct";
    }

    @GetMapping("/reviews")
    public String reviewsPage(Model model) {
        model.addAttribute("reviewsToApprove", reviewsToApprove);
        return "adminreviews";
    }

    @GetMapping("/reviews/remove/{id}")
    public String removeReview(@PathVariable UUID id) {
        reviewsToApprove.removeIf(review -> review.getId().equals(id));
        reviewService.removeReview(reviewService.getReviewById(id).get());
        return "redirect:/admin/reviews";
    }

    @GetMapping("/reviews/approve/{id}")
    public String approveReview(@PathVariable UUID id) {
        reviewsToApprove.removeIf(review -> review.getId().equals(id));
        Review review = reviewService.getReviewById(id).get();
        review.setApproved(true);
        reviewService.saveReview(review);
        return "redirect:/admin/reviews";
    }
}
