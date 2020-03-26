package ru.gribnoff.springshop.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "purchases")
public class Purchase extends PersistableEntity {
    private Double price;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<CartRecord> cartRecords;

    @ManyToOne
    @JoinColumn(name = "shopuser")
    private ShopUser shopUser;
}
