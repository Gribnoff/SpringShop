package ru.gribnoff.springshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gribnoff.springshop.persistence.entities.Purchase;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {}