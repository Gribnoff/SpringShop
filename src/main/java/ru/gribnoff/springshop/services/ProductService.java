package ru.gribnoff.springshop.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ru.gribnoff.springshop.exceptions.ProductNotFoundException;
import ru.gribnoff.springshop.persistence.entities.Product;
import ru.gribnoff.springshop.persistence.entities.enums.ProductCategory;
import ru.gribnoff.springshop.persistence.repositories.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findOneById(UUID uuid) throws ProductNotFoundException {
        return productRepository.findById(uuid).orElseThrow(
            () -> new ProductNotFoundException("Oops! Product " + uuid + " wasn't found!")
        );
    }

    public List<Product> findAll(Integer category) {
        return category == null
                ? productRepository.findAll()
                : productRepository.findAllByCategory(ProductCategory.values()[category]);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

}