package ru.gribnoff.springshop.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cart_records")
@EqualsAndHashCode(callSuper = true)
@ApiModel("запись в корзине покупок")
public class CartRecord extends PersistableEntity {
    @ApiModelProperty(notes = "количество единиц товара")
    private Integer quantity;
    @ApiModelProperty(notes = "цена единицы товара")
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product")
    @ApiModelProperty(notes = "товар магазина")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "purchase")
    @JsonManagedReference
    @ApiModelProperty(notes = "заказ, к которому относится эта покупка")
    private Purchase purchase;

    public CartRecord(Product product) {
        this.product = product;
        this.quantity = 1;
        this.price = product.getPrice();
    }
}
