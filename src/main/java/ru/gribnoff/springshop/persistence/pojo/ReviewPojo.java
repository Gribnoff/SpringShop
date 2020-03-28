package ru.gribnoff.springshop.persistence.pojo;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewPojo {
    private String comment;
    private UUID product;
}
