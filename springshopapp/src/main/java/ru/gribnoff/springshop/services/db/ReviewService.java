package ru.gribnoff.springshop.services.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.gribnoff.springshop.persistence.entities.Product;
import ru.gribnoff.springshop.persistence.entities.Review;
import ru.gribnoff.springshop.persistence.entities.ShopUser;
import ru.gribnoff.springshop.persistence.repositories.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<Review> getReviewById(UUID id) {
        return reviewRepository.findById(id);
    }

    public Optional<List<Review>> getUnapprovedReviews() {
        return reviewRepository.findByApproved(false);
    }

    @Transactional
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    @Transactional
    public void removeReview(Review review) {
        reviewRepository.delete(review);
    }

    @Transactional
    public void removeReviewById(UUID id) {
        reviewRepository.removeById(id);
    }
}
