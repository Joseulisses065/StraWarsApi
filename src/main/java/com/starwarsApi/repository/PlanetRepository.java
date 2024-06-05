package com.starwarsApi.repository;

import com.starwarsApi.domain.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet , Long> {
}
