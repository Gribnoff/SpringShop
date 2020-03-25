package ru.gribnoff.springshop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.gribnoff.springshop.persistence.entities.Image;
import ru.gribnoff.springshop.persistence.repositories.ImageRepository;
import ru.gribnoff.springshop.util.UUIDValidator;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.IOException;

import java.nio.charset.MalformedInputException;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final static String STATIC_ICONS_PATH = "/static/icons/";
    private final static String STATIC_IMAGES_PATH = "/static/images/";

    private final ImageRepository imageRepository;

    private List<String> getImagesForProduct(UUID productId) {
        return imageRepository.getImagesNamesByProductId(productId);
    }

    private Image getImageNameById(UUID imageId) {
        return imageRepository.findImageById(imageId);
    }

    public BufferedImage loadFileAsResource(String id) throws IOException {
        try {
            Resource resource = UUIDValidator.isUUID(id)
                    ? new ClassPathResource(STATIC_IMAGES_PATH + getImageNameById(UUID.fromString(id)).getName())
                    : new ClassPathResource(STATIC_ICONS_PATH + id);

            return resource.exists()
                    ? ImageIO.read(resource.getFile())
                    : ImageIO.read(new ClassPathResource(STATIC_ICONS_PATH + "image_not_found.png").getFile());
        } catch (MalformedInputException | IllegalArgumentException ex) {
            return null;
        }
    }
}