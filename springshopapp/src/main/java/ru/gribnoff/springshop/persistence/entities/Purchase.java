package ru.gribnoff.springshop.persistence.entities;

import lombok.*;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "purchases")
public class Purchase extends PersistableEntity {
	private Double price;
	private String email;
	private String phone;

	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
	private List<CartRecord> cartRecords;

	@ManyToOne
	@JoinColumn(name = "shopuser")
	private ShopUser shopUser;

	@ManyToMany
	@JoinTable(name = "purchase_product",
			joinColumns = @JoinColumn(name = "purchase", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "product", referencedColumnName = "id"))
	private List<Product> products;
}
