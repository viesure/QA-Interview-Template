# RestAssured and Selenium Test Automation Framework

- Author: Attila Gyori
- Email: gyoriattila@yahoo.com
- Website: https://attila-gyori.com

![Playwright](assets/selenium.png)

> Selenium 4.20.0 with Spring Boot, Page Object Model, cucumber and extent report project with Selenium Grid



![Selenium][Selenium]
![Spring][Spring]
![Cucumber][Cucumber]
![CucumberReport][CucumberReport]
![ExtentReport][ExtentReport]
![RestAssured][RestAssured]

## 🚀 Run Tests (test set runs with 2 threads parallel by default)

1. ___Local Execution___:
   1. mvn clean test -Dcucumber.filter.tags="@api"
   2. mvn clean test -Dcucumber.filter.tags="@ui" -Dbrowser=chrome
      1. to specify the browser use -Dbrowser=chrome, edge or firefox
2. ___Remote Execution___: 
   1. mvn clean test -Dcucumber.filter.tags="@ui" -Dbrowser=chrome -Dspring.profiles.active=remote-with-local-grid-compose
      1. Requires docker. It utilizes spring-boot-docker-compose to spin up selenium grid using docker-compose:
      2. default browser: Chrome. To specify the browser use -Dbrowser=edge or firefox
3. ___Custom Remote:___
   1. mvn clean test -Dremote -Dselenium.grid.url=url -Dbrowser=chrome
      1. url: of selenium grid 
      2. default browser: Chrome. To specify the browser use -Dbrowser=edge or firefox
4. ___Execute Framework Unit tests:___
   1. mvn clean test -Punittest
5. ___Parallel Execution:___
   1. By default dataproviderthreadcount is 1 running tests in one thread
      1. use -Ddataproviderthreadcount=2 to run test set in parallel
   2. @api test set can not be run in parallel cause tests modifies the state of the SUT
   3. @ui test set can be run in parallel

## 📋Test Output
1. ___Cucumber report:___ /reports/cucumber-report.html
2. ___Extent Report:___ /reports/date-folder/
3. ___Videos___ in remote execution: /reports/videos/
4. ___Logs:___ /logs

## 🐙 Selenium Grid
1. ___Selenium Dashboard:___ localhost:4444
2. ___Remote VNC___ to harness container: localhost:7900 (Chrome), localhost:7901 (Edge),localhost:7902 (Firefox),

## 🐞 Findings: see reports in archived-reports folder
1. ___missing "conditionId"___ field from response payload: GET /weather:
steps to reproduce:
GET /weather -> "conditionId" field is missing from reponse payload
2. ___Incorrect icon extension___: GET /weather:
in case of drizzle weather the icon field in the payload contains .jpeg extension instead of .png (/weather/condition with PUT payload
{
"condition": "4"
})
3. ___Temperature rounding___: it doesnt follow the "normal rounding rules" or at least not the same as java Math.round
In the description it says: "Uses normal rounding rules" So it requires further investigation
In case of 10 celsius it is "cold" instead of "mild" - but also can be some rounding issue.
Testing with Postman: -2°C = 28.400°F by the GET endpoint
"weather": {
"tempInFahrenheit": 30,
"tempInCelsius": -2
}
4. ___Missing description field suffix___: GET /weather:
in Case of 20 celsius "warm" is missing
expected: "The weather is warm"
but was: "The weather is "
"description": "The weather is ",
"weather": {
"tempInFahrenheit": 68,
"tempInCelsius": 20
}

5. ___front-end no hit found___: type Sydney in upper search box and press enter: not hits found, see attached screenshot

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[Selenium]: https://img.shields.io/badge/Selenium-blue
[Cucumber]: https://img.shields.io/badge/Cucumber-8A2BE2
[RestAssured]: https://img.shields.io/badge/RestAssured-122BE2
[Spring]: https://img.shields.io/badge/Spring-purple
[CucumberReport]: https://img.shields.io/badge/Cucumber_Report-orange
[ExtentReport]: https://img.shields.io/badge/Extent_Report-green