package ru.gribnoff.springshop.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.gribnoff.springshop.config.WebSecurityConfigurationTest;
import ru.gribnoff.springshop.controllers.ProductController;
import ru.gribnoff.springshop.services.db.ImageService;
import ru.gribnoff.springshop.services.db.ProductService;
import ru.gribnoff.springshop.services.db.ReviewService;
import ru.gribnoff.springshop.services.db.ShopUserService;

import java.io.IOException;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
@ContextConfiguration(classes = WebSecurityConfigurationTest.class)
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class ProductControllerMVCTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AmqpTemplate amqpTemplate;
	@MockBean
	private ImageService imageService;
	@MockBean
	private ProductService productService;
	@MockBean
	private ShopUserService shopUserService;
	@MockBean
	private ReviewService reviewService;

	@Before
	public void setUp() throws IOException {
//		ProductPojo productPojo = new ObjectMapper().readValue(new File("mocks/pineapple.json"), ProductPojo.class);
//		when(productService.save(productPojo, null)).;
	}

	@Test
	public void testMustSaveProduct() throws Exception {

		mockMvc.perform(post("/products")
				.content("mocks/pineapple.json")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(model().size(1))
				.andExpect(model().attributeExists(""))
				.andExpect(redirectedUrl("/admin"));
	}
}
