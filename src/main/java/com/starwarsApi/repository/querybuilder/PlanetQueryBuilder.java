package com.starwarsApi.repository.querybuilder;

import com.starwarsApi.domain.model.Planet;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

@Component
public class PlanetQueryBuilder implements com.starwarsApi.repository.querybuilder.interfaces.PlanetQueryBuilder {
    public Example<Planet> makeQuery(Planet planet){
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        return Example.of(planet,exampleMatcher);
    }
}
