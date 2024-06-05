package com.starwarsApi.service;

import com.starwarsApi.domain.model.Planet;
import com.starwarsApi.exception.UniqueDataException;
import com.starwarsApi.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;

    @Transactional
    public Planet save(Planet planet) {
        try {
            return planetRepository.save(planet);
        }catch (DataIntegrityViolationException ex){
            throw new UniqueDataException("Unique date violation "+ex.getMessage());
        }
        }
}
