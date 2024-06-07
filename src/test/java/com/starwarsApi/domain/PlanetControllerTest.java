package com.starwarsApi.domain;

import static com.starwarsApi.common.PlanetConstatnts.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starwarsApi.domain.model.Planet;
import com.starwarsApi.exception.UniqueDataException;
import com.starwarsApi.service.PlanetService;
import com.starwarsApi.web.controller.PlanetController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//@Sql(scripts = "/sql/import_planets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidDate_ReturnCreated() throws Exception {
        when(planetService.create(PLANET)).thenReturn(PLANET);
        mockMvc.perform(post("/api/v1/planets").content(objectMapper.writeValueAsString(PLANET)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void createPlanet_WithInvalidDate_ReturnBadRequest() throws Exception {
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("", "", "");

        mockMvc.perform(post("/api/v1/planets").content(objectMapper.writeValueAsString(emptyPlanet))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/api/v1/planets").content(objectMapper.writeValueAsString(invalidPlanet))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createPlanet_ExistingName_ReturnConflict() throws Exception {
        when(planetService.create(any())).thenThrow(UniqueDataException.class);
        mockMvc.perform(post("/api/v1/planets").content(objectMapper.writeValueAsString(PLANET))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() throws Exception {
        when(planetService.findById(1L)).thenReturn(PLANET);
        mockMvc.perform(get("/api/v1/planets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/planets/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() throws Exception {
        when(planetService.findByName(PLANET.getName())).thenReturn(PLANET);
        mockMvc.perform(get("/api/v1/planets/name/" + PLANET.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/planets/name/alex"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllPlanets_ReturnsFilteredPlanets() throws Exception {
        when(planetService.findAll(PAGEABLE, null, null)).thenReturn(PLANETS);
        when(planetService.findAll(PAGEABLE, TATOOINE.getTerrain(), TATOOINE.getClimate())).thenReturn(PLANETS_ONE);

        mockMvc
                .perform(
                        get("/api/v1/planets?" + String.format("terrain=%s&climate=%s", TATOOINE.getTerrain(), TATOOINE.getClimate())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]").value(TATOOINE));

        mockMvc.perform(get("/api/v1/planets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getAllPlanets_ReturnsNoPlanets() throws Exception {
        when(planetService.findAll(PAGEABLE, null, null)).thenReturn(Page.empty());
        mockMvc
                .perform(
                        get("/api/v1/planets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void removePlanets_WithExistingId_ReturnsNoPlanets() throws Exception {
        mockMvc.perform(delete("/api/v1/planets/1")).andExpect(status().isNoContent());

    }

    @Test
    public void removePlanets_WithUnexistingId_ReturnsNoPlanets() throws Exception {
        final Long planetId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(planetService).delete(planetId);
        mockMvc.perform(delete("/api/v1/planets/"+planetId))
                .andExpect(status().isNotFound());
    }
}