package ru.gribnoff.springshop.persistence.entities;

import lombok.*;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
@EqualsAndHashCode(callSuper = true)
public class Review extends PersistableEntity {
    @ManyToOne
    @JoinColumn(name = "shopuser")
    private ShopUser shopUser;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    private String comment;

    @OneToOne
    @JoinColumn(name = "image")
    private Image image;
}
