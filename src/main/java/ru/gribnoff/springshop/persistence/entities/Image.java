package ru.gribnoff.springshop.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.gribnoff.springshop.persistence.entities.util.PersistableEntity;

import javax.persistence.*;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
@EqualsAndHashCode(callSuper = true)
public class Image extends PersistableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

}