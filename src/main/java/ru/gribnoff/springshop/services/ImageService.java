package ru.gribnoff.springshop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.gribnoff.springshop.exceptions.UnsupportedMediaTypeException;
import ru.gribnoff.springshop.persistence.entities.Image;
import ru.gribnoff.springshop.persistence.entities.Product;
import ru.gribnoff.springshop.persistence.entities.ShopUser;
import ru.gribnoff.springshop.persistence.entities.enums.ImageCategory;
import ru.gribnoff.springshop.persistence.repositories.ImageRepository;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${files.storepath.product-images}")
    private Path PRODUCT_IMAGES_PATH;

    @Value("${files.storepath.icons}")
    private Path ICONS_PATH;

    @Value("${files.storepath.review-photos}")
    private Path REVIEW_PHOTO_PATH;

    @Value("${files.storepath.images-dump}")
    private Path IMAGES_DUMP;

    private final ImageRepository imageRepository;

    private List<String> getImagesForProduct(UUID productId) {
        return imageRepository.getImagesNamesByProductId(productId);
    }

    private Image getImageNameById(UUID imageId) {
        return imageRepository.findImageById(imageId);
    }

    public BufferedImage loadFileAsResource(String id, ImageCategory category) throws IOException {
        Resource resource = null;
        switch (category) {
            case PRODUCT_IMAGE:
                resource = new UrlResource(PRODUCT_IMAGES_PATH
                        .resolve(getImageNameById(UUID.fromString(id)).getName())
                        .normalize()
                        .toUri());
                break;
            case REVIEW_PHOTO:
                resource = new UrlResource(REVIEW_PHOTO_PATH
                        .resolve(getImageNameById(UUID.fromString(id)).getName())
                        .normalize()
                        .toUri());
                break;
            case ICON:
                resource = new UrlResource(ICONS_PATH
                        .resolve(id)
                        .normalize()
                        .toUri());
                break;
        }

        return resource.exists()
                ? ImageIO.read(resource.getFile())
                : ImageIO.read(new UrlResource(ICONS_PATH
                .resolve("image_not_found.png")
                .normalize()
                .toUri())
                .getFile());
    }

    @Transactional
    public Image uploadImage(MultipartFile image, ImageCategory category) throws IOException {
        String uploadedFileName = image.getOriginalFilename();
        if (image.isEmpty() || uploadedFileName == null)
            throw new FileNotFoundException("File not specified");
        else {
            if (isValidImageExtension(image)) {
                Path path = Paths.get(getPathByCategory(category).resolve(uploadedFileName).toUri().normalize());
                image.transferTo(path);
            }
        }
        return imageRepository.save(new Image(uploadedFileName));
    }

    public Image uploadReviewPhoto(MultipartFile image, Product product, ShopUser shopUser) throws IOException {
        String fileExtension = getExtensionByStringHandling(image.getOriginalFilename()).get();
        String uploadedFileName = product.getTitle()
                + shopUser.getFirstName()
                + shopUser.getLastName()
                + "." + fileExtension;
        if (image.isEmpty())
            throw new FileNotFoundException("File not specified");
        else {
            if (isValidImageExtension(image)) {
                Path path = Paths.get(getPathByCategory(ImageCategory.REVIEW_PHOTO).resolve(uploadedFileName).toUri().normalize());
                image.transferTo(path);
            }
        }
        return imageRepository.save(new Image(uploadedFileName));
    }

    private Path getPathByCategory(ImageCategory category) {
        switch (category) {
            case ICON:
                return ICONS_PATH;
            case PRODUCT_IMAGE:
                return PRODUCT_IMAGES_PATH;
            case REVIEW_PHOTO:
                return REVIEW_PHOTO_PATH;
            default:
                return IMAGES_DUMP;
        }
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private boolean isValidImageExtension(MultipartFile image) {

        switch (Objects.requireNonNull(image.getContentType())) {

            case MediaType.IMAGE_JPEG_VALUE:
            case MediaType.IMAGE_PNG_VALUE:
            case MediaType.IMAGE_GIF_VALUE:
                return true;

            default:
                throw new UnsupportedMediaTypeException("Error! This file type is not supported!");

        }
    }
}