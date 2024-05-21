Feature: Weather UI

  Background:
    Given user navigates to "https://openweathermap.org/"

  @ui
  Scenario: Verify placeholder text in search field on home page
    Then city search field placeholder text is: "Weather in your city"


  @ui
  Scenario Outline: Verify placeholder text in search field on home page
    When user types "Sydney" in the city search field and press enter
    When user clicks on "<cityTitle>" link
    Then city title is correct: "<cityTitle>"
    And time shown is correct by zone: "<timeZone>"

    Examples:
      | cityTitle  | timeZone         |
      | Sydney, AU | Australia/Sydney |
