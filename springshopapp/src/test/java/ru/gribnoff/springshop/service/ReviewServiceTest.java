package ru.gribnoff.springshop.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gribnoff.springshop.persistence.entities.Product;
import ru.gribnoff.springshop.persistence.entities.Review;
import ru.gribnoff.springshop.services.db.ProductService;
import ru.gribnoff.springshop.services.db.ReviewService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration(exclude = {RabbitAutoConfiguration.class})
public class ReviewServiceTest {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ProductService productService;
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Before
	public void setUp() {
		try {
			List<Product> products = new ObjectMapper().readValue(new ClassPathResource("mocks/products.json").getFile(), new TypeReference<List<Product>>() {});
			products.forEach(product -> productService.save(product));

			List<Review> reviews = new ObjectMapper().readValue(new ClassPathResource("mocks/reviews.json").getFile(), new TypeReference<List<Review>>() {});
			reviews.forEach(review -> reviewService.saveReview(review));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getReviewsByProductPineappleTest() throws Exception {
		Product testProduct = productService.findOneById(UUID.fromString("9a977f7f-92c3-44c2-a475-2b246b36cc30"));
		Optional<List<Review>> reviewsOpt = reviewService.getReviewsByProduct(testProduct);
		List<Review> reviews = reviewsOpt.orElse(new ArrayList<>());
		assertEquals(3, reviews.size());
	}
}
