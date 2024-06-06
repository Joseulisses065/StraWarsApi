package com.starwarsApi.web.dto.mapper;

import com.starwarsApi.domain.model.Planet;
import com.starwarsApi.web.dto.PlanetCreateDto;
import com.starwarsApi.web.dto.PlanetResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class PlanetMapper {

    public static PlanetResponseDto toDto(Planet planet) {
        return new ModelMapper().map(planet, PlanetResponseDto.class);
    }

    public static Planet toPlanet(PlanetCreateDto dto) {
        return new ModelMapper().map(dto, Planet.class);
    }

    public static Page<PlanetResponseDto> toListDto(Page<Planet> entity) {
        List<PlanetResponseDto> planets = entity.getContent().stream().map(planet -> new  ModelMapper().map(planet,PlanetResponseDto.class)).toList();
        return new PageImpl<>(planets, PageRequest.of(entity.getNumber(),entity.getSize()),entity.getTotalElements());
    }
}
