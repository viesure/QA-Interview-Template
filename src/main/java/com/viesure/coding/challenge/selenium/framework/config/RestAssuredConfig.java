package com.viesure.coding.challenge.selenium.framework.config;

import com.viesure.coding.challenge.selenium.framework.annotation.LazyConfiguration;
import com.viesure.coding.challenge.selenium.framework.annotation.ThreadScopeBean;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;

import static io.restassured.RestAssured.given;

@LazyConfiguration
@Slf4j
public class RestAssuredConfig {

    @Value("${api.base.url}")
    private URL baseUrl;

    @ThreadScopeBean
    public RequestSpecification getRequestSpecification() {
        log.info("Instantiation of RestAssured");
        RestAssured.baseURI = baseUrl.toString();
        return given().contentType(ContentType.JSON).accept(ContentType.ANY);
    }

}
