package com.starwarsApi.common;

import com.starwarsApi.domain.model.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PlanetConstatnts {
    public static final Planet PLANET = new Planet("axel", "sub tor", "mandalo");
    public static final Planet INVALIDPLANET = new Planet("", "", "");

    public static final Planet TATOOINE = new Planet(100L, "Tatooine", "arid", "desert");
    public static final Planet ALDERAAN = new Planet(200L, "Alderaan", "temperate", "grasslands, mountains");
    public static final Planet YAVINIV = new Planet(300L, "Yavin IV", "temperate, tropical", "jungle, rainforests");

    public static final Pageable PAGEABLE = PageRequest.of(1, 10);
    public static final List<Planet> PLANET_LIST = List.of(TATOOINE, ALDERAAN, YAVINIV);
    public static final Page<Planet> PLANETS = new PageImpl<>(PLANET_LIST, PAGEABLE, PLANET_LIST.size());
    public static final Page<Planet> PLANETS_ONE = new PageImpl<>(List.of(TATOOINE), PAGEABLE, PLANET_LIST.size());

}
