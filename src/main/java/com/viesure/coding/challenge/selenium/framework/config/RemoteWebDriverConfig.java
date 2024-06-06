package com.viesure.coding.challenge.selenium.framework.config;

import com.viesure.coding.challenge.selenium.framework.annotation.LazyConfiguration;
import com.viesure.coding.challenge.selenium.framework.annotation.BrowserScopeBean;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;

import java.net.URL;

@Profile({"remote", "remote-with-local-grid-compose"})
@LazyConfiguration
public class RemoteWebDriverConfig {

    @Value("${selenium.grid.url}")
    private URL url;

    @BrowserScopeBean
    @ConditionalOnProperty(name = "browser", havingValue = "firefox")
    public WebDriver remoteFirefoxDriver() {
        return new RemoteWebDriver(this.url, new FirefoxOptions());
    }

    @BrowserScopeBean
    @ConditionalOnProperty(name = "browser", havingValue = "edge")
    public WebDriver remoteEdgeDriver() {
        return new RemoteWebDriver(this.url, new EdgeOptions());
    }

    @BrowserScopeBean
    @ConditionalOnMissingBean
    public WebDriver remoteChromeDriver() {
        return new RemoteWebDriver(this.url, new ChromeOptions());
    }

}
