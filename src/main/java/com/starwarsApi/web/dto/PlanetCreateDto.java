package com.starwarsApi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String climate;
    @NotNull
    @NotBlank(message = "the terrain cant´t be empty")
    private String terrain;
}
