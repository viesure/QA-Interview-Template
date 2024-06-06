package com.viesure.coding.challenge.selenium.page.weatherapp;

import com.viesure.coding.challenge.selenium.framework.annotation.LazyAutowired;
import com.viesure.coding.challenge.selenium.framework.annotation.Page;
import com.viesure.coding.challenge.selenium.page.AbstractPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

//ToDo it could be decomposed into views and multiple page objects
@Page
public class WeatherAppHomePage extends AbstractPage {

    @LazyAutowired
    WebDriverWait webDriverWait;

    @FindBy(css = "#desktop-menu input[type='text']")
    private WebElement citySearchInput;

    @FindBy(css = "#forecast_list_ul td a")
    private List<WebElement> sydneySearchHitLinks;

    @FindBy(css = "#weather-widget .current-container.mobile-padding h2")
    private WebElement cityTitleHeader;

    @FindBy(css = "#weather-widget .current-container.mobile-padding .orange-text")
    private WebElement dateAndTimeSpan;

    public String getCitySearchInputPlaceholder() {
        return citySearchInput.getAttribute("placeholder");
    }

    //ToDo in case of multiple pages it would return the next page

    public void searchCity(String city) {
        citySearchInput.sendKeys(city);
        new Actions(driver).sendKeys(Keys.ENTER).perform();
        webDriverWait.until(ExpectedConditions.urlContains("https://openweathermap.org/find?q=" + city));
    }

    public void clickOnSearchHitCity(String city) {
        webDriverWait.until(ExpectedConditions.titleContains("Find - OpenWeatherMap"));
        WebElement webElement = sydneySearchHitLinks.stream().filter(w -> w.getText().contains(city)).findFirst().orElseThrow(() -> new NoSuchElementException("City: " + city + " not found as link"));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
    }

    public String getCityTitle() {
        webDriverWait.until(ExpectedConditions.titleContains("Weather forecast - OpenWeatherMap"));
        webDriverWait.until(ExpectedConditions.visibilityOf(cityTitleHeader));
        return cityTitleHeader.getText();
    }

    public String getDateAndTime() {
        webDriverWait.until(ExpectedConditions.titleContains("Weather forecast - OpenWeatherMap"));
        return dateAndTimeSpan.getText();
    }

    @Override
    public boolean isAt() {
        return false;
    }


}
