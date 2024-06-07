package com.starwarsApi.web.controller;

import com.starwarsApi.domain.model.Planet;
import com.starwarsApi.exception.EntityNotFounException;
import com.starwarsApi.service.PlanetService;
import com.starwarsApi.web.dto.PlanetCreateDto;
import com.starwarsApi.web.dto.PlanetResponseDto;
import com.starwarsApi.web.dto.mapper.PlanetMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@Tag(name = "Planets",description = "All features of planet")
@RestController
@RequestMapping("/api/v1/planets")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;

    @Operation(description = "Create new planet")
    @PostMapping
    public ResponseEntity<PlanetResponseDto> create(@RequestBody @Valid PlanetCreateDto planet){
        Planet entity = planetService.create(PlanetMapper.toPlanet(planet));
        URI locale = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(locale).body(PlanetMapper.toDto(entity));
    }
    @Operation(description = "Find planet by id")
    @GetMapping("/{id}")
    public ResponseEntity<PlanetResponseDto> findById(@PathVariable Long id){
        try {
            Planet entity = planetService.findById(id);
            return ResponseEntity.ok(PlanetMapper.toDto(entity));
        }catch (RuntimeException ex){
            return ResponseEntity.notFound().build();
        }

    }
    @Operation(description = "Find planet by name")
    @GetMapping("/name/{name}")
    public ResponseEntity<PlanetResponseDto> findByName(@PathVariable String name){
        try {
            Planet entity = planetService.findByName(name);
            return ResponseEntity.ok(PlanetMapper.toDto(entity));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping
    public ResponseEntity<Page<PlanetResponseDto>> listAll(@RequestParam(required = false) String terrain, @RequestParam(required = false) String climate, Pageable page){
        Page<Planet> entity= planetService.findAll(page,terrain,climate);
        return ResponseEntity.ok(PlanetMapper.toListDto(entity));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            planetService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException ex){
            return ResponseEntity.notFound().build();
        }

    }

}
