package ru.gribnoff.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.gribnoff.paymentservice")
public class SoapProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoapProducerApplication.class, args);
    }
}
