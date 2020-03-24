package ru.gribnoff.springshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gribnoff.springshop.persistence.entities.Image;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    @Query(value = "SELECT images.name FROM images INNER JOIN products p ON images.id = p.image WHERE p.id = :id", nativeQuery = true)
    String obtainImageNameByProductId(@Param("id") UUID id);
}