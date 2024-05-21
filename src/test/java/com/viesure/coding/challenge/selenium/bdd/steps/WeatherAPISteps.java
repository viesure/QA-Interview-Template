package com.viesure.coding.challenge.selenium.bdd.steps;

import com.viesure.coding.challenge.selenium.framework.context.ContextContainer;
import com.viesure.coding.challenge.selenium.framework.context.ContextKeys;
import com.viesure.coding.challenge.selenium.framework.service.TemperatureConverter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class WeatherAPISteps {
    @Autowired
    RequestSpecification requestSpecification;

    @Autowired
    ContextContainer<Response> cucumberContextResponseValue;

    @Autowired
    ContextContainer<String> cucumberContextStringValue;

    @Autowired
    ContextContainer<Integer> cucumberContextIntValue;


    @Autowired
    TemperatureConverter temperatureConverter;

    private final static int OK = 200;


    @Given("an endpoint with relative path: {string} to fetch weather details")
    public void checkAPIAvailable(String url) {
        cucumberContextStringValue.getMap().put(ContextKeys.URL, url);

        requestSpecification.when().get(url).then().statusCode(OK).log().ifValidationFails();
    }

    @When("user calls the endpoint: {string} with GET request")
    public void callAPIWithGET(String relativeUrl) {

        Response response = requestSpecification.get(relativeUrl);
        cucumberContextResponseValue.getMap().put(ContextKeys.RESPONSE, response);
    }


    @When("user sends PUT request with payload containing conditionId: {int} to endpoint: {string}")
    public void putConditionId(int conditionId, String conditionEndpoint) {
        Map<String, Integer> jsonPayload = new HashMap<>();
        jsonPayload.put("condition", conditionId);

        requestSpecification.body(jsonPayload).when()
                .put(conditionEndpoint).then()
                .statusCode(OK);
    }

    @Then("the response contains the corresponding {string} with {string}")
    public void verifyConditionIdIsInLineWithCondition(String conditionField, String expectedConditionValue) {
        RestAssured.baseURI = "https://backend-interview-tokio.tools.gcp.viesure.io";
        Response res = requestSpecification
                .get(cucumberContextStringValue.getMap().get(ContextKeys.URL))
                .then().extract().response();
        String actualCondition = res.jsonPath().getString(conditionField);
        Assertions.assertThat(actualCondition).isEqualTo(expectedConditionValue).withFailMessage("Expected actualCondition: " + expectedConditionValue + " Actual actualCondition: " + actualCondition);
    }

    @When("endpoint will send back a response with correct schema: city: string, condition: string, icon: string, description: string, conditionId: integer whole number, tempInFahrenheit: integer whole number and tempInCelsius: integer whole number")
    public void verifySchema() {
        final String schemaPath = "fixtures/api/weather-api-response-schema.json";
        cucumberContextResponseValue.getMap().get(ContextKeys.RESPONSE).then().assertThat()
                .body(matchesJsonSchemaInClasspath(schemaPath)).log().ifValidationFails();
    }
    @When("user sends PUT request with payload containing {string}: value from {int} converted to fahrenheit to endpoint: {string}")
    public void putTempInFahrenheit(String tempInFahrenheitField, int celsius, String tempEndpoint) {
        var fahrenheit = temperatureConverter.convertCelsiusToFahrenheit(celsius);
        cucumberContextIntValue.getMap().put(ContextKeys.CELSIUS, celsius);
        cucumberContextIntValue.getMap().put(ContextKeys.FAHRENHEIT, fahrenheit);
        Map<String, Integer> jsonPayload = new HashMap<>();
        jsonPayload.put(tempInFahrenheitField, fahrenheit);

        requestSpecification.body(jsonPayload).when()
                .put(tempEndpoint).then()
                .statusCode(OK);
    }

    @Then("the response contains correct temperature values in both fields: {string} and {string}")
    public void verifyTemperatures(String tempInFahrenheitField, String tempInCelsiusField) {

        var celsiusCurrent =  Integer.valueOf(getTemperatureFieldValue(tempInCelsiusField));
        var fahrenheitCurrent = Integer.valueOf(getTemperatureFieldValue(tempInFahrenheitField));
        var celsiusExpected = cucumberContextIntValue.getMap().get(ContextKeys.CELSIUS);
        var fahrenheitExpected = cucumberContextIntValue.getMap().get(ContextKeys.FAHRENHEIT);

        assertAll("Verify fields: " + tempInFahrenheitField + " and " + tempInCelsiusField,
                () -> assertEquals(fahrenheitCurrent, fahrenheitExpected),
                () -> assertEquals(celsiusCurrent, celsiusExpected));

    }

    @Then("the conditionID field gives an ID of the current condition as follows:")
    public void verifyConditionsMap(DataTable conditions) {
        final String conditionIdProperty = "conditionId";
        final String conditionProperty = "condition";

        Map<Integer, String> dataMap = conditions.asMap(Integer.class, String.class);
        Response res = cucumberContextResponseValue.getMap().get(ContextKeys.RESPONSE).then().extract().response();
        String condition = res.jsonPath().getString(conditionProperty);
        log.info("Condition in response: " + condition);
        Optional<Integer> conditionIDCurrent = dataMap.entrySet().stream().filter(s -> s.getValue().equals(condition)).findFirst().map(s -> s.getKey());
        Integer conditionIDActualValue = conditionIDCurrent.orElseGet(() -> 0);
        Assertions.assertThat(dataMap).containsKey(conditionIDActualValue).withFailMessage("Condition: " + condition + " is not in the expected values!");

        String conditionIdPropertyValue = res.jsonPath().getString(conditionIdProperty);
        assertAll("Verify conditionId field and the corresponding condition",
                () -> assertNotNull(conditionIdPropertyValue),
                () -> assertEquals(conditionIDCurrent, conditionIdPropertyValue));

    }

    @Then("the {string} field value with {string} makes up the {string} field value")
    public void verifyIconPropertyValue(String conditionPropName, String extension, String iconPropName) {

        Response res = cucumberContextResponseValue.getMap().get(ContextKeys.RESPONSE).then().extract().response();
        String condition = res.jsonPath().getString(conditionPropName);
        String iconPropValue = condition + extension;
        res.then().body(iconPropName, equalTo(iconPropValue)).onFailMessage(iconPropName + " is not " + iconPropValue).log().ifValidationFails();
    }

    @Then("the value of {string} field is a whole number with 0 digits")
    public void verifyTempField(String temperatureFieldName) {
        String temperatureValue = getTemperatureFieldValue(temperatureFieldName);
        Assertions.assertThatCode(() -> Integer.parseInt(temperatureValue))
                .withFailMessage("Expected the temperature field name to be an integer, but it wasn't: %s", temperatureValue)
                .doesNotThrowAnyException();

    }

    @Then("the exchange of {string} to {string} is correct")
    public void verifyFahrenheitToCelsiusConversion(String tempInFahrenheitField, String tempInCelsiusField) {
        Integer fahrenheitValue = Integer.valueOf(getTemperatureFieldValue(tempInFahrenheitField));
        Integer celsiusValue = Integer.valueOf(getTemperatureFieldValue(tempInCelsiusField));
        Assertions.assertThat(celsiusValue).isEqualTo(temperatureConverter.convertFahrenheitToCelsius(fahrenheitValue));
    }


    @Then("the {string} field contains leading text by rule {string}: prefix: {string} with weather condition: {string} as suffix determined by celsius: {int} field")
    public void verifyDescriptionText(String descriptionField, String rule, String descriptionPrefix, String weatherDescription, int tempInCelsius) {
        descriptionPrefix = descriptionPrefix.trim();
        weatherDescription = weatherDescription.trim();
        var descriptionFieldExpected = String.format("%s %s",descriptionPrefix,weatherDescription);
        log.info(String.format("field: Description should contain %s by rule: %s for celsius: %s", descriptionFieldExpected,rule, tempInCelsius));
        Response res = cucumberContextResponseValue.getMap().get(ContextKeys.RESPONSE).then().extract().response();
        String descriptionCurrent = res.jsonPath().getString(descriptionField);
        Assertions.assertThat(descriptionCurrent).isEqualTo(descriptionFieldExpected);
    }

    private String getTemperatureFieldValue(String temperatureFieldName) {
        final String parentField = "weather";
        final String dot = ".";
        String temperatureValue = cucumberContextResponseValue.getMap().get(ContextKeys.RESPONSE).then().extract().response().jsonPath().getString(String.format("%s%s%s", parentField, dot, temperatureFieldName));
        return temperatureValue;
    }


}
