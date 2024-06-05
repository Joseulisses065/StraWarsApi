package com.starwarsApi.web.controller;

import com.starwarsApi.domain.model.Planet;
import com.starwarsApi.service.PlanetService;
import com.starwarsApi.web.dto.PlanetCreateDto;
import com.starwarsApi.web.dto.PlanetResponseDto;
import com.starwarsApi.web.dto.mapper.PlanetMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/planets")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;

    @PostMapping
    public ResponseEntity<PlanetResponseDto> create(@RequestBody @Valid PlanetCreateDto planet){
        Planet entity = planetService.save(PlanetMapper.toPlanet(planet));
        URI locale = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(locale).body(PlanetMapper.toDto(entity));
    }
}
