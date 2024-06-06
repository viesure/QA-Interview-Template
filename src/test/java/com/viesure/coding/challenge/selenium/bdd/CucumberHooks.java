package com.viesure.coding.challenge.selenium.bdd;

import com.viesure.coding.challenge.selenium.framework.annotation.LazyAutowired;
import com.viesure.coding.challenge.selenium.framework.service.ScreenshotService;
import com.viesure.coding.challenge.selenium.framework.service.StringService;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
public class CucumberHooks {

    @LazyAutowired
    private ScreenshotService screenshotService;

    @LazyAutowired
    private ApplicationContext applicationContext;

    @LazyAutowired
    private StringService stringService;

    @Value("${video.path}")
    private String videoPath;


    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
//            scenario.attach(this.screenshotService.getScreenshot(), "image/png", scenario.getName());
            try {
                if (this.applicationContext.getBean(TakesScreenshot.class) != null) {
                    final byte[] screenshot = this.applicationContext.getBean(TakesScreenshot.class).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", scenario.getName());
                }
            } catch (NoSuchBeanDefinitionException exception) {
                log.info("No TakesScreenshot bean " + exception.getMessage());
            }

        }

    }


    @After(order = 11000)
    public void afterScenario(Scenario scenario) {
        //ToDo: Embed video into reports

        final String remote = "remote";
        final String extension = ".mp4";
        String scenarioNameFileNameFriendlyWithDate = stringService.convertScenarioNameToFileNameWithDate(scenario.getName());

        Map<String, WebDriver> beansOfType = this.applicationContext.getBeansOfType(WebDriver.class);
        beansOfType.forEach((k, v) -> {
            SessionId sessionId = this.applicationContext.getBean(RemoteWebDriver.class).getSessionId();

            if (k.toLowerCase().contains(remote)) {

                Path fileToMovePath = Paths.get(videoPath, sessionId.toString() + extension);
                Path targetPath = Paths.get(videoPath, scenarioNameFileNameFriendlyWithDate);
                if (fileToMovePath.toFile().exists()) {
                    try {
                        Files.move(fileToMovePath, targetPath);

                    } catch (IOException e) {
                        log.error("Error processing file: " + fileToMovePath.getFileName());
                        throw new RuntimeException(e);
                    }

                } else {
                    log.error("Error Selenium Grid video file no exist: " + fileToMovePath.getFileName());
                }

            }
        });
    }

    @After(order = 10000)
    public void afterScenarioQuit(Scenario scenario) {
        try {
            if (this.applicationContext.getBean(WebDriver.class) != null) {
                this.applicationContext.getBean(WebDriver.class).quit();
            }
        } catch (NoSuchBeanDefinitionException exception) {
            log.info("No Webdriver bean " + exception.getMessage());
        }
    }

}
