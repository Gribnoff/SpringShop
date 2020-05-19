package ru.gribnoff.springshop.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	@JsonBackReference
	private List<CartRecord> cartRecords;

	@ManyToOne
	@JoinColumn(name = "shopuser")
	@JsonManagedReference
	private ShopUser shopUser;

	@ManyToMany
	@JoinTable(name = "purchase_product",
			joinColumns = @JoinColumn(name = "purchase", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "product", referencedColumnName = "id"))
	private List<Product> products;
}
