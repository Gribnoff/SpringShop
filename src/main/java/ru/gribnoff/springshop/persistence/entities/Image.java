package ru.gribnoff.springshop.persistence.entities;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "images")
public class Image implements Serializable {

    private static final long SUID = 1L;

    @Id
    private UUID id;

    private String name;

    @ManyToOne
    @JoinTable(
            name = "product_image",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Product product;
}