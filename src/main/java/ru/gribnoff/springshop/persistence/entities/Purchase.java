package ru.gribnoff.springshop.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "purchases")
public class Purchase extends PersistableEntity {
    private Double price;
    private String address;
    private String phone;
}
