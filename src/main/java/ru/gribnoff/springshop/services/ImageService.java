package ru.gribnoff.springshop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gribnoff.springshop.persistence.entities.Image;
import ru.gribnoff.springshop.persistence.repositories.ImageRepository;
import ru.gribnoff.springshop.util.UUIDValidator;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${files.storepath.images}")
    private Path IMAGES_PATH;

    @Value("${files.storepath.icons}")
    private Path ICONS_PATH;

    private final ImageRepository imageRepository;

    private List<String> getImagesForProduct(UUID productId) {
        return imageRepository.getImagesNamesByProductId(productId);
    }

    private Image getImageNameById(UUID imageId) {
        return imageRepository.findImageById(imageId);
    }

    public BufferedImage loadFileAsResource(String id) throws IOException {
            Resource resource = UUIDValidator.isUUID(id)
                    ? new UrlResource(IMAGES_PATH
                        .resolve(getImageNameById(UUID.fromString(id)).getName())
                        .normalize()
                        .toUri())
                    : new UrlResource(ICONS_PATH
                        .resolve(id)
                        .normalize()
                        .toUri());

            return resource.exists()
                    ? ImageIO.read(resource.getFile())
                    : ImageIO.read(new UrlResource(ICONS_PATH
                        .resolve("image_not_found.png")
                        .normalize()
                        .toUri())
                        .getFile());
    }

    public Image uploadImage(MultipartFile image, String title) {
        return null;
    }
}