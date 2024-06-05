package com.starwarsApi;

import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculatorTest {
    @Test
    public void testSoma() {
        Calculator cal = new Calculator();
        assertThat(cal.sum(1,2)).isEqualTo(3);

    }
}
