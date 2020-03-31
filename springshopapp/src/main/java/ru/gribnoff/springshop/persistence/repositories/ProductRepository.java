package ru.gribnoff.springshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gribnoff.springshop.persistence.entities.Product;
import ru.gribnoff.springshop.persistence.entities.enums.ProductCategory;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAll();
    List<Product> findAllByCategory(ProductCategory category);
}