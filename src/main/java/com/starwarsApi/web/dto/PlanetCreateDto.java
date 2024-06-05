package com.starwarsApi.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanetCreateDto {
    @NotBlank(message = "the name cant´t be empty")
    private String name;
    @NotBlank(message = "the climate cant´t be empty")
    private String climate;
    @NotBlank(message = "the terrain cant´t be empty")
    private String terrain;
}
