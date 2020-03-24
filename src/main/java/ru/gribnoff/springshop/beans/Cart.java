package ru.gribnoff.springshop.beans;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.gribnoff.springshop.persistence.entities.CartRecord;
import ru.gribnoff.springshop.persistence.entities.Product;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart implements Serializable {
    private static final long SUID = 1L;

    private List<CartRecord> cartRecords;
    private Double price;

    @PostConstruct
    public void init() {
        cartRecords = new ArrayList<>();
    }

    public void add(Product product) {
        for (CartRecord cartRecord : cartRecords) {
            if (cartRecord.getProduct().getId().equals(product.getId())) {
                cartRecord.setQuantity(cartRecord.getQuantity() + 1);
                cartRecord.setPrice(cartRecord.getQuantity() * cartRecord.getProduct().getPrice());
                recalculatePrice();
                return;
            }
        }
        cartRecords.add(new CartRecord(product));
        recalculatePrice();
    }

    public void removeByProductId(UUID productId) {
        for (int i = 0; i < cartRecords.size(); i++) {
            if (cartRecords.get(i).getProduct().getId().equals(productId)) {
                cartRecords.remove(i);
                recalculatePrice();
                return;
            }
        }
    }

    private void recalculatePrice() {
        price = 0.;
        cartRecords.forEach(
                cartRecord -> price += cartRecord.getPrice()
        );
    }

}
