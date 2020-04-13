package ru.gribnoff.springshop.services.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gribnoff.springshop.persistence.entities.Purchase;
import ru.gribnoff.springshop.persistence.repositories.PurchaseRepository;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Transactional
    public Purchase makePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

}