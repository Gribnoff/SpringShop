package ru.gribnoff.springshop.persistence.entities;

import lombok.*;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
@EqualsAndHashCode(callSuper = true)
public class Review extends PersistableEntity {

    private String comment;

    @ManyToOne
    @JoinColumn(name = "shopuser")
    private ShopUser shopUser;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;
}
