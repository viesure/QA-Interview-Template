package com.viesure.coding.challenge.selenium.framework.config;

import com.viesure.coding.challenge.selenium.framework.annotation.LazyConfiguration;
import com.viesure.coding.challenge.selenium.framework.annotation.ThreadScopeBean;
import com.viesure.coding.challenge.selenium.framework.context.CucumberContext;
import lombok.extern.slf4j.Slf4j;


@LazyConfiguration
@Slf4j
public class CucumberContextConfig {

    @ThreadScopeBean
    public CucumberContext getCucumberContext() {
        log.info("Creating cucumber context");
        return new CucumberContext();
    }

}
