package com.viesure.coding.challenge.selenium.framework.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class StringServiceTestCase {
    private static StringService stringService;

    @BeforeAll
    private static void setUp() {
        stringService = new StringService();
    }

    @Test
    void convertScenarioNameToFileNameWithDate() {
        String actual = stringService.convertScenarioNameToFileNameWithDate("asd asd dasd");
        Assertions.assertThat(actual).startsWith("asd_asd_dasd-").matches("^\\w+-\\d{2}-\\d{1}-\\d{4}-\\d{2}-\\d{2}-\\d{2}-\\d{3}.mp4$");
    }

    @Test
    void convertScenarioNameToFileNameWithDate_input_empty() {
        assertThatThrownBy(() -> {
            stringService.convertScenarioNameToFileNameWithDate("");
        }).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("File name is empty!");

    }

}