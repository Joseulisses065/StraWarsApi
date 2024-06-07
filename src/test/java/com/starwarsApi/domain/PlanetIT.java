package com.starwarsApi.domain;

import static com.starwarsApi.common.PlanetConstatnts.PLANET;
import static com.starwarsApi.common.PlanetConstatnts.TATOOINE;
import static org.assertj.core.api.Assertions.assertThat;

import com.starwarsApi.domain.model.Planet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Sql(scripts = "/sql/import_planets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql/delete_planets.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanetIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createPlanet_ReturnsCreated() {
        ResponseEntity<Planet> sut = restTemplate.postForEntity("/api/v1/planets", PLANET, Planet.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody().getId()).isNotNull();
        assertThat(sut.getBody().getName()).isEqualTo(PLANET.getName());
        assertThat(sut.getBody().getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(sut.getBody().getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void getPlanet_ReturnPlanet() {
        ResponseEntity<Planet> sut = restTemplate.getForEntity("/api/v1/planets/{id}", Planet.class, TATOOINE.getId());
        assertThat(sut.getBody()).isEqualTo(TATOOINE);
    }

    @Test
    public void getPlanetByName_ReturnPlanet() {
        ResponseEntity<Planet> sut = restTemplate.getForEntity("/api/v1/planets/"+TATOOINE.getName(), Planet.class);
        assertThat(sut.getBody()).isEqualTo(TATOOINE);
    }

    @Test
    public void getPlanets_ReturnAllPlanet() {
        ResponseEntity<Planet> sut = restTemplate.getForEntity("/api/v1/planets", Planet.class);
    }
/*
    @Test
    public void getPlanets_ByClimate_ReturnAllPlanet() {
        ResponseEntity<Planet[]> sut = restTemplate.getForEntity("/api/v1/planets?climate="+TATOOINE.getClimate(), Planet[].class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).hasSize(1);
        assertThat(sut.getBody()[0]).isEqualTo(TATOOINE);
    }

    @Test
    public void getPlanets_ByTerrain_ReturnAllPlanet() {
        ResponseEntity<Planet[]> sut = restTemplate.getForEntity("/api/v1/planets?terrain="+TATOOINE.getTerrain(), Planet[].class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).hasSize(1);
        assertThat(sut.getBody()[0]).isEqualTo(TATOOINE);
    }*/

    @Test
    public void removePlanet_ReturnNoContent() {
        ResponseEntity<Void> sut =restTemplate.exchange("/api/v1/planets/"+TATOOINE.getId(),HttpMethod.DELETE,null, Void.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


}
