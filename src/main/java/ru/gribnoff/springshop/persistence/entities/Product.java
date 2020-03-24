package ru.gribnoff.springshop.persistence.entities;

import lombok.Data;
import ru.gribnoff.springshop.persistence.entities.enums.ProductCategory;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    private UUID id;

    private String title;

    private Date added;

    private Double price;

    private String description;

    private boolean available;

    @OneToMany
    @JoinTable(
            name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images;

    @Enumerated(EnumType.ORDINAL)
    private ProductCategory category;
}
