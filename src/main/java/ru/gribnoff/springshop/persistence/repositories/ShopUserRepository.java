package ru.gribnoff.springshop.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.gribnoff.springshop.persistence.entities.ShopUser;

import java.util.UUID;

public interface ShopUserRepository extends CrudRepository<ShopUser, UUID> {
    ShopUser findShopUserByPhone(String phone);
    boolean existsByPhone(String phone);
}
