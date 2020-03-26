package ru.gribnoff.springshop.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.*;
import java.util.Collection;
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

    @ManyToMany
    @JoinTable(
            name = "shopuser_role",
            joinColumns = @JoinColumn(name = "shopuser"),
            inverseJoinColumns = @JoinColumn(name = "role")
    )
    private Collection<Role> roles;

    @OneToMany(mappedBy = "shopUser")
    private List<Purchase> purchase;
}
