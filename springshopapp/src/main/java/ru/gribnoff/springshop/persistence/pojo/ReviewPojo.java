package ru.gribnoff.springshop.persistence.pojo;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewPojo {
    private UUID productId;
    private String comment;
    private String captchaCode;
}
