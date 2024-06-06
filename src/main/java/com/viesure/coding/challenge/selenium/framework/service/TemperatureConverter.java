package com.viesure.coding.challenge.selenium.framework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Lazy
@Service
public class TemperatureConverter {


    /**
     * Convert Fahrenheit To Celsius with normal rounding
     *
     * @param fahrenheit
     * @return celsius
     */
    public int convertFahrenheitToCelsius(int fahrenheit) {
        var celsius = (fahrenheit - 32) * 5 / 9;
        int celsiusRound = Math.round(celsius);
        log.info("Converting temperature fahrenheit: " + fahrenheit + " to celsius: " + celsiusRound);
        return celsiusRound;
    }

    /**
     * Convert Celsius To Fahrenheit with normal rounding
     *
     * @param celsius
     * @return fahrenheit
     */
    public int convertCelsiusToFahrenheit(int celsius) {
        var fahrenheit = ((celsius * 9) / 5) + 32;
        int fahrenheitRound = (int) Math.round(fahrenheit);
        log.info("Converting temperature celsius: " + celsius + " to fahrenheit: " + fahrenheitRound);
        return fahrenheitRound;
    }
}
