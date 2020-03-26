package ru.gribnoff.springshop.persistence.pojo;

import lombok.Data;
import ru.gribnoff.springshop.persistence.entities.enums.ProductCategory;

@Data
public class ProductPojo {
    private String title;
    private String description;
    private Double price;
    private boolean available;
    private ProductCategory category;
}
