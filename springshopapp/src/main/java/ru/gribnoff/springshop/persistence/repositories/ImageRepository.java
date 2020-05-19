package ru.gribnoff.springshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gribnoff.springshop.persistence.entities.Image;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    @Query(value = "SELECT images.name FROM shop.images " +
            "JOIN shop.product_image pi ON images.id = pi.image " +
            "JOIN shop.products p on pi.product = p.id " +
            "WHERE p.id = :id", nativeQuery = true)
    List<String> findImageNamesByProductId(@Param("id") UUID id);

//    @Query(value = "SELECT name FROM public.images " +
//            "WHERE id = :id", nativeQuery = true)
//    String getImageNameByImageId(@Param("id") UUID id);
    Image findImageById(@Param("id") UUID id);
}