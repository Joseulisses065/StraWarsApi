package com.starwarsApi.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "planets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Planet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(name = "name",nullable = false,unique = true)
    private String name;
    @NotEmpty
    @Column(name = "climate",nullable = false)
    private String climate;
    @NotEmpty
    @Column(name = "terrain",nullable = false)
    private String terrain;

    public Planet(String climate, String terrain) {
        this.climate = climate;
        this.terrain = terrain;
    }

    public Planet(String name, String climate, String terrain) {
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }

    @Override
    public boolean equals(Object obj) {
        return  EqualsBuilder.reflectionEquals(obj, this);
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", climate='" + climate + '\'' +
                ", terrain='" + terrain + '\'' +
                '}';
    }
}
