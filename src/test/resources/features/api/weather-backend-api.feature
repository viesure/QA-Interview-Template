Feature: Weather backend API endpoint

  Background:
    Given an endpoint with relative path: "/weather" to fetch weather details


  @api
  Scenario: api: /weather responses with correct fields and types
    When user calls the endpoint: "/weather" with GET request
    Then endpoint will send back a response with correct schema: city: string, condition: string, icon: string, description: string, conditionId: integer whole number, tempInFahrenheit: integer whole number and tempInCelsius: integer whole number


  @api
  Scenario: /weather api GET response contains condition with the relevant conditionID
    When user calls the endpoint: "/weather" with GET request
    Then the conditionID field gives an ID of the current condition as follows:
      | 1 | clear   |
      | 2 | windy   |
      | 3 | mist    |
      | 4 | drizzle |
      | 5 | dust    |

  @api
  Scenario Outline: /weather api GET response contains condition with the relevant 'conditionID' by active condition change with PUT
    When user sends PUT request with payload containing conditionId: <conditionIdValue> to endpoint: "/weather/condition"
    When user calls the endpoint: "/weather" with GET request
    Then the response contains the corresponding "condition" with "<conditionValue>"
    Then the "condition" field value with ".png" makes up the "icon" field value

    Examples:
      | conditionIdValue | conditionValue |
      | 1                | clear          |
      | 2                | windy          |
      | 3                | mist           |
      | 4                | drizzle        |
      | 5                | dust           |


  @api
  Scenario: /weather api GET response contains 'icon' field with value: 'condition' field value with .png extension
    When user calls the endpoint: "/weather" with GET request
    Then the "condition" field value with ".png" makes up the "icon" field value

  @api
  Scenario: /weather api GET response contains both tempInFahrenheit and tempInCelsius fields with correct exchange
    When user calls the endpoint: "/weather" with GET request
    Then the value of "tempInFahrenheit" field is a whole number with 0 digits
    And the value of "tempInCelsius" field is a whole number with 0 digits
    And the exchange of "tempInFahrenheit" to "tempInCelsius" is correct

  @api
  Scenario Outline: /weather api GET response contains description field returning a description text of the current weather
    When user sends PUT request with payload containing "tempInFahrenheit": value from <celsius> converted to fahrenheit to endpoint: "/weather/temp"
    When user calls the endpoint: "/weather" with GET request
    Then the "description" field contains leading text by rule "<rule>": prefix: "The weather is" with weather condition: "<description>" as suffix determined by celsius: <celsius> field
    Then the response contains correct temperature values in both fields: "tempInFahrenheit" and "tempInCelsius"
    Examples:
      | rule          | description | celsius |
      | celsius <= 0  | freezing    | 0       |
      | celsius <= 0  | freezing    | -1      |
      | celsius < 10  | cold        | 1       |
      | celsius < 10  | cold        | 9       |
      | celsius < 20  | mild        | 10      |
      | celsius < 20  | mild        | 19      |
      | celsius < 25  | warm        | 20      |
      | celsius < 25  | warm        | 24      |
      | celsius >= 25 | hot         | 25      |
      | celsius >= 25 | hot         | 26      |
