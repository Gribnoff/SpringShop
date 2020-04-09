package ru.gribnoff.pdfmock.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Service
public class FlyerService {

    private final Resource flyer = new ClassPathResource("pdf/flyer.pdf");

    public byte[] getFlyer() throws IOException {
        File file = flyer.getFile();
        return file != null ? Files.readAllBytes(file.toPath()) : new byte[0];
    }

}