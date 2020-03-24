package ru.gribnoff.springshop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.gribnoff.springshop.persistence.repositories.ImageRepository;
import ru.gribnoff.springshop.util.UUIDValidator;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.charset.MalformedInputException;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private List<String> getImagesForProduct(UUID productId) {
        return imageRepository.getImagesNamesByProductId(productId);
    }

    private String getImageNameById(UUID imageId) {
        return imageRepository.getImageNameByImageId(imageId);
    }

    public BufferedImage loadFileAsResource(String id) throws IOException {
        try {
            if (UUIDValidator.isUUID(id)) {
                String imageName = getImageNameById(UUID.fromString(id));
                Resource resource = new ClassPathResource("/static/images/" + imageName);
                if (resource.exists()) {
                    return ImageIO.read(resource.getFile());
                } else {
                    log.error("Image not found!");
                    throw new FileNotFoundException("File " + imageName + " not found!");
                }
            } else {
                log.error("Page parameter is not UUID");
                throw new IllegalArgumentException("Wrong argument type!");
            }
        } catch (MalformedInputException | FileNotFoundException | IllegalArgumentException ex) {
            return null;
        }
    }

}