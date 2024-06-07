package com.starwarsApi.domain;

import static com.starwarsApi.common.PlanetConstatnts.INVALIDPLANET;
import static com.starwarsApi.common.PlanetConstatnts.PLANET;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.starwarsApi.domain.model.Planet;
import com.starwarsApi.exception.EntityNotFounException;
import com.starwarsApi.repository.PlanetRepository;
import com.starwarsApi.repository.querybuilder.PlanetQueryBuilder;
import com.starwarsApi.service.PlanetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {
    //@Autowired
    @InjectMocks
    private PlanetService planetService;
    //@MockBean
    @Mock
    private PlanetRepository planetRepository;
    @Mock
    private PlanetQueryBuilder planetQueryBuilder;

    //Padrão de nomeclatura operção_estado_retorno
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        when(planetRepository.save(PLANET)).thenReturn(PLANET);
        //sut = system under test
        Planet sut = planetService.create(PLANET);
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        when(planetRepository.save(INVALIDPLANET)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> planetService.create(INVALIDPLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanetResponseDTO() {
        when(planetRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(PLANET));
        Planet sut = planetService.findById(10L);
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingId_ThrowsEntityNotFoundException() {
        when(planetRepository.findById(100L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> planetService.findById(100L))
                .isInstanceOf(EntityNotFounException.class);
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanetResponseDTO() {
        when(planetRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(PLANET));
        Planet sut = planetService.findByName("axel");
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingName_ThrowsEntityNotFounException() {
        when(planetRepository.findByName("axe")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> planetService.findByName("axe"))
                .isInstanceOf(EntityNotFounException.class);
    }

    @Test
    public void listPlanets_ReturnAllPlanets() {
        Planet query = new Planet(PLANET.getClimate(), PLANET.getTerrain());
        Example<Planet> queryEx = Example.of(query);

        Pageable pageable = PageRequest.of(1, 1);
        Page<Planet> planets = new PageImpl<>(List.of(PLANET), pageable, 1L);

        when(planetQueryBuilder.makeQuery(any(Planet.class))).thenReturn(queryEx);
        when(planetRepository.findAll(queryEx, pageable)).thenReturn(planets);

        Page<Planet> sut = planetService.findAll(pageable, "teste", "teste");

        // Verificar se a página retornada contém todos os elementos esperados
        assertThat(sut).containsExactlyElementsOf(planets);
        assertThat(sut).isNotEmpty();
        assertThat(sut.stream().toList().get(0)).isEqualTo(PLANET);

    }

    @Test
    public void listPlanets_ReturnAllNoPlanets() {
        Planet query = new Planet("t", "t");
        Example<Planet> queryEx = Example.of(query);

        Pageable pageable = PageRequest.of(1, 1);
        Page<Planet> empty = Page.empty(pageable);

        when(planetQueryBuilder.makeQuery(any(Planet.class))).thenReturn(queryEx);
        when(planetRepository.findAll(queryEx, pageable)).thenReturn(empty);

        Page<Planet> sut = planetService.findAll(pageable, "teste", "teste");

        assertThat(sut).isEmpty();
        assertThat(sut.getTotalElements()).isEqualTo(0);
        assertThat(sut.getContent()).isEmpty();
    }

    @Test
    public void removePlanet_ByExistingId_doesThrowsEntityNotFoundException() {
        assertThatCode(() -> planetRepository.deleteById(1L)).doesNotThrowAnyException();
    }

    @Test
    public void getPlanet_ByUnxistingId_ThrowsEntityNotFoundException() {
        doThrow(new RuntimeException()).when(planetRepository).deleteById(99L);
        assertThatThrownBy(() -> planetRepository.deleteById(99L)).isInstanceOf(RuntimeException.class);


    }
}