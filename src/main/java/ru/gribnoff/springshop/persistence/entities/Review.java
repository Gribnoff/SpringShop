package ru.gribnoff.springshop.persistence.entities;

import lombok.*;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
