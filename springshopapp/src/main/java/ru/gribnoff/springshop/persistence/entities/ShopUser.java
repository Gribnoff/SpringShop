package ru.gribnoff.springshop.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.gribnoff.springshop.persistence.entities.enums.Role;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "shopusers")
@EqualsAndHashCode(callSuper = true)
public class ShopUser extends PersistableEntity {
    private String phone;
    private String email;
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "shopUser")
    @JsonBackReference
    private List<Purchase> purchases;
}
