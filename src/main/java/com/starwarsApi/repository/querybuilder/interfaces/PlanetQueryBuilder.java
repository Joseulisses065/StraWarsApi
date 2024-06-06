package com.starwarsApi.repository.querybuilder.interfaces;

import com.starwarsApi.domain.model.Planet;
import org.springframework.data.domain.Example;

public interface PlanetQueryBuilder {
    public Example<Planet> makeQuery(Planet planet);
}


