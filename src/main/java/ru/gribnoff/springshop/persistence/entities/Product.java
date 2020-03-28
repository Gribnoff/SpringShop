package ru.gribnoff.springshop.persistence.entities;

import lombok.*;
import ru.gribnoff.springshop.persistence.entities.enums.ProductCategory;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@EqualsAndHashCode(callSuper = true)
public class Product extends PersistableEntity {

    private String title;
    private Date added;
    private Double price;
    private String description;
    private boolean available;

    @OneToMany
    @JoinTable(
            name = "product_image",
            joinColumns = @JoinColumn(name = "product"),
            inverseJoinColumns = @JoinColumn(name = "image")
    )
    private List<Image> images;

    @Enumerated(EnumType.ORDINAL)
    private ProductCategory category;
}
