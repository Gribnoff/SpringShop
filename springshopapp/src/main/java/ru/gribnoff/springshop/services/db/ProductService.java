package ru.gribnoff.springshop.services.db;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gribnoff.springshop.exceptions.ProductNotFoundException;
import ru.gribnoff.springshop.persistence.entities.Image;
import ru.gribnoff.springshop.persistence.entities.Product;
import ru.gribnoff.springshop.persistence.pojo.ProductPojo;
import ru.gribnoff.springshop.persistence.repositories.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
	@PersistenceContext
	private EntityManager entityManager;

    private final ProductRepository productRepository;

    public Product findOneById(UUID uuid) throws ProductNotFoundException {
        return productRepository.findById(uuid).orElseThrow(
            () -> new ProductNotFoundException("Oops! Product " + uuid + " wasn't found!")
        );
    }

	public List<Product> findAll() {
		return productRepository.findAll();
	}


	public List<Product> findAll(Integer category, Integer minPrice, Integer maxPrice, Boolean notAvailable) {
        /*
        return category == null ?
                productRepository.findAll() :
                productRepository.findAllByCategory(ProductCategory.values()[maxPrice]);
        */

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

		Root<Product> root = criteriaQuery.from(Product.class);
		List<Predicate> predicates = new ArrayList<>();

		if (category != null) {
			predicates.add(criteriaBuilder.equal(root.get("category"), category));
		}

		if (minPrice != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
		}

		if (maxPrice != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
		}

		if (notAvailable == null || !notAvailable) {
            predicates.add(criteriaBuilder.isTrue(root.get("available")));
        }

		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));

        return entityManager.createQuery(criteriaQuery).getResultList();
	}

    @Transactional
    public String save(ProductPojo productPogo, List<Image> images) {

        Product product = Product.builder()
                .added(new Date())
                .title(productPogo.getTitle())
                .description(productPogo.getDescription())
                .price(productPogo.getPrice())
                .available(productPogo.isAvailable())
                .category(productPogo.getCategory())
                .images(images)
                .build();

        productRepository.save(product);
        log.info("New Product has been successfully added! {}", product);
        return "redirect:/";
    }
}