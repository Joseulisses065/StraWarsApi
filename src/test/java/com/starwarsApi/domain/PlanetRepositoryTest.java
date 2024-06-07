package com.starwarsApi.domain;

import com.starwarsApi.common.PlanetConstatnts;

import static com.starwarsApi.common.PlanetConstatnts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.starwarsApi.domain.model.Planet;
import com.starwarsApi.repository.PlanetRepository;
import com.starwarsApi.repository.querybuilder.PlanetQueryBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ComponentScan(basePackages = "com.starwarsApi.repository.querybuilder")
public class PlanetRepositoryTest {
    @AfterEach
    public void afterEach() {
        PLANET.setId(null);
    }

    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private PlanetQueryBuilder planetQueryBuilder;

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        Planet planet = planetRepository.save(PLANET);
        Planet sut = testEntityManager.find(Planet.class, planet.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo("axel");
        assertThat(sut.getClimate()).isEqualTo("sub tor");
        assertThat(sut.getTerrain()).isEqualTo("mandalo");
    }

    @Test
    public void createPlanet_WithUnvalidData_ThrowsException() {
        Planet emptyPlanet = new Planet();
        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> planetRepository.save(INVALIDPLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createPlanet_WithExistingName_ThrowsException() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet);
        planet.setId(null);
        assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> planetoOpt = planetRepository.findById(planet.getId());
        assertThat(planetoOpt).isNotEmpty();
        assertThat(planetoOpt.get()).isEqualTo(planet);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty() {
        Optional<Planet> planetoOpt = planetRepository.findByName(PLANET.getName());
        assertThat(planetoOpt).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        Optional<Planet> planetoOpt = planetRepository.findByName(planet.getName());
        assertThat(planetoOpt).isNotEmpty();
        assertThat(planetoOpt.get()).isEqualTo(planet);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() {
        Optional<Planet> planetoOpt = planetRepository.findById(1L);
        assertThat(planetoOpt).isEmpty();
    }
/*
    @Sql(scripts = "/sql/import_planets.sql")
    @Test
    public void getAllPlanets_ReturnsFilteredPlanets() {
        Example<Planet> queryNoFilter = planetQueryBuilder.makeQuery(new Planet());
        Example<Planet> queryFilter = planetQueryBuilder.makeQuery(new Planet(TATOOINE.getClimate(), TATOOINE.getTerrain()));

        List<Planet> responseWithFilter = planetRepository.findAll(queryFilter);
        List<Planet> responseWithoutFilter = planetRepository.findAll(queryNoFilter);

        assertThat(responseWithoutFilter).isNotEmpty();
        assertThat(responseWithoutFilter).hasSize(3);
        assertThat(responseWithFilter).isNotEmpty();
        assertThat(responseWithFilter).hasSize(1);
        assertThat(responseWithFilter.get(0)).isEqualTo(TATOOINE);


    }
*/
    @Test
    public void getAllPlanets_ReturnsNoPlanets() {
        Example<Planet> queryNoFilter = planetQueryBuilder.makeQuery(new Planet());
        List<Planet> response = planetRepository.findAll(queryNoFilter);
        assertThat(response).isEmpty();
    }

    @Test
    public void removePlanets_WithExistingId_RemovesPlanetFromDatabase() throws Exception {
        Planet planet = testEntityManager.persistFlushFind(PLANET);

        planetRepository.deleteById(planet.getId());

        Planet removerPlanet = testEntityManager.find(Planet.class,planet.getId());
        assertThat(removerPlanet).isNull();
    }

  /*
    @Test
    public void removePlanets_WithUnexistingId_ThrowsException(){
        assertThatThrownBy(()->planetRepository.deleteById(0L)).isInstanceOf(EmptyResultDataAccessException.class);

    }
*/

}
