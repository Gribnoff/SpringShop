package ru.gribnoff.springshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.gribnoff.springshop.persistence.entities.Product;
import ru.gribnoff.springshop.persistence.entities.Review;
import ru.gribnoff.springshop.persistence.entities.ShopUser;
import ru.gribnoff.springshop.persistence.repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Optional<List<Review>> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }

    public Optional<List<Review>> getReviewsByShopUser(ShopUser shopUser) {
        return reviewRepository.findByShopUser(shopUser);
    }

    @Transactional
    public void save(Review review) {
        reviewRepository.save(review);
    }
}
