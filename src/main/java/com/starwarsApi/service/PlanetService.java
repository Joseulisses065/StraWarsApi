package com.starwarsApi.service;

import com.starwarsApi.domain.model.Planet;
import com.starwarsApi.exception.EntityNotFounException;
import com.starwarsApi.exception.UniqueDataException;
import com.starwarsApi.repository.PlanetRepository;
import com.starwarsApi.repository.querybuilder.PlanetQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetQueryBuilder planetQueryBuilder;

    private final PlanetRepository planetRepository;

    @Transactional
    public Planet create(Planet planet) {
        try {
            return planetRepository.save(planet);
        } catch (DataIntegrityViolationException ex) {
            throw new UniqueDataException("Unique date violation " + ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Planet findById(Long id) {
        return planetRepository.findById(id).orElseThrow(
                ()->new EntityNotFounException(String.format("Planet with id=%s not found",id))
        );
    }

    public Planet findByName(String name) {
        return planetRepository.findByName(name).orElseThrow(
                ()->new EntityNotFounException(String.format("Planet with name= %s not found",name)));
    }

    public Page<Planet> findAll(Pageable page, String terrain, String climate) {
        Example<Planet> query = planetQueryBuilder.makeQuery(new Planet(climate, terrain));
        return planetRepository.findAll(query,page);
    }

    public void delete(Long id) {
            planetRepository.deleteById(id);
    }
}
