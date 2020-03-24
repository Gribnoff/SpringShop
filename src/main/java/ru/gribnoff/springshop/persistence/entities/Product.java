package ru.gribnoff.springshop.persistence.entities;

import lombok.Data;
import ru.gribnoff.springshop.persistence.entities.enums.ProductCategory;

import javax.persistence.*;
import java.util.Date;
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

    @OneToOne
    @JoinColumn(name = "image")
    private Image image;

    @Enumerated(EnumType.ORDINAL)
    private ProductCategory category;
}
