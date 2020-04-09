package ru.gribnoff.pdfmock.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gribnoff.pdfmock.services.FlyerService;

import java.io.IOException;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class FlyerController {

    private final FlyerService flyerService;

    @GetMapping("/flyers")
    public ResponseEntity<byte[]> getFlyerPDF() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(flyerService.getFlyer(), headers, HttpStatus.OK);
    }

}