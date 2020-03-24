package ru.gribnoff.springshop.persistence.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}