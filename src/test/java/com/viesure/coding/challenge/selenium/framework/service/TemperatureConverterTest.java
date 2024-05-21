package com.viesure.coding.challenge.selenium.framework.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TemperatureConverterTest {
    private static TemperatureConverter temperatureConverter;

    @BeforeAll
    private static void setUp() {
        temperatureConverter = new TemperatureConverter();
    }

    @Test
    void convertFahrenheitToCelsius_1() {
        int actual = temperatureConverter.convertFahrenheitToCelsius(45);
        Assertions.assertThat(actual).isEqualTo(7);
    }

    @Test
    void convertFahrenheitToCelsius_rounding() {
        int actual = temperatureConverter.convertFahrenheitToCelsius(-2);
        Assertions.assertThat(actual).isEqualTo(-18);
    }

    @Test
    void convertCelsiusToFahrenheit() {
        int actual = temperatureConverter.convertCelsiusToFahrenheit(5);
        Assertions.assertThat(actual).isEqualTo(41);
    }

    @Test
    void convertCelsiusToFahrenheit_rounding() {
        int actual = temperatureConverter.convertCelsiusToFahrenheit(-9);
        Assertions.assertThat(actual).isEqualTo(16);
    }
}