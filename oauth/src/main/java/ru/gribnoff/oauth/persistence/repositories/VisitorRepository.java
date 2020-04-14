package ru.gribnoff.oauth.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gribnoff.oauth.persistence.entities.Visitor;

import java.util.Optional;
import java.util.UUID;

public interface VisitorRepository extends JpaRepository<Visitor, UUID> {
    Optional<Visitor> findByUsername(String username);
}
