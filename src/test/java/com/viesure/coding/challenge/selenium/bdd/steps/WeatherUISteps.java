package com.viesure.coding.challenge.selenium.bdd.steps;

import com.viesure.coding.challenge.selenium.framework.annotation.LazyAutowired;
import com.viesure.coding.challenge.selenium.framework.service.DateTimeConverter;
import com.viesure.coding.challenge.selenium.page.weatherapp.WeatherAppHomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest
@Slf4j
public class WeatherUISteps {
    @LazyAutowired
    WeatherAppHomePage weatherAppHomePage;
    @LazyAutowired
    DateTimeConverter dateTimeConverter;



    @Given("user navigates to {string}")
    public void navigateToHomePage(String homePageUrl) {
        weatherAppHomePage.goTo(homePageUrl);
    }

    @Then("city search field placeholder text is: {string}")
    public void verifyCitySearchFieldPlaceholderText(String placeholderTextExpected) {
        String citySearchInputPlaceholderActual = weatherAppHomePage.getCitySearchInputPlaceholder();
        Assertions.assertThat(citySearchInputPlaceholderActual).isEqualTo(placeholderTextExpected);
    }

    @When("user types {string} in the city search field and press enter")
    public void searchCity(String city) {

        weatherAppHomePage.searchCity(city);

    }

    @When("user clicks on {string} link")
    public void clickOnCity(String city) {

        weatherAppHomePage.clickOnSearchHitCity(city.trim());


    }

    @Then("city title is correct: {string}")
    public void verifyCityTitle(String cityTitleExpected) {

        String cityTitleActual = weatherAppHomePage.getCityTitle();
        Assertions.assertThat(cityTitleActual.trim()).isEqualTo(cityTitleExpected.trim());
    }

    @Then("time shown is correct by zone: {string}")
    public void verifyTime(String timeZone) {
        String timeExpected = dateTimeConverter.getCurrentTimeWithTimeZone(timeZone);
        String dateAndTimeActual = weatherAppHomePage.getDateAndTime();
        Assertions.assertThat(dateAndTimeActual).endsWith(timeExpected);
    }

}
