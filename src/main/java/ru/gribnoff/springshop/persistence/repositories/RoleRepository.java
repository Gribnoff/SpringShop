package ru.gribnoff.springshop.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.gribnoff.springshop.persistence.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Byte> {
    Role findByName(String name);
}
