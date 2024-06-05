package com.starwarsApi.domain;

import static com.starwarsApi.common.PlanetConstatnts.INVALIDPLANET;
import static com.starwarsApi.common.PlanetConstatnts.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import  static org.mockito.Mockito.when;

import com.starwarsApi.domain.model.Planet;
import com.starwarsApi.repository.PlanetRepository;
import com.starwarsApi.service.PlanetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {
    //@Autowired
    @InjectMocks
    private PlanetService planetService;
    //@MockBean
    @Mock
    private PlanetRepository planetRepository;

    //Padrão de nomeclatura operção_estado_retorno
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        when(planetRepository.save(PLANET)).thenReturn(PLANET);
        //sut = system under test
        Planet sut = planetService.save(PLANET);
        assertThat(sut).isEqualTo(PLANET);
    }
    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){
        when(planetRepository.save(INVALIDPLANET)).thenThrow(RuntimeException.class);
        assertThatThrownBy(()->planetService.save(INVALIDPLANET)).isInstanceOf(RuntimeException.class);
    }
}
