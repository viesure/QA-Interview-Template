package com.viesure.coding.challenge.selenium.framework.context;

import com.viesure.coding.challenge.selenium.framework.annotation.LazyConfiguration;
import com.viesure.coding.challenge.selenium.framework.annotation.ThreadScopeBean;
import io.restassured.response.Response;

//ToDo create more comprehensive context

@LazyConfiguration
public class CucumberContext<T> {

    @ThreadScopeBean
    public ContextContainer<Response> getContextContainerResponse() {
        return new ContextContainer<Response>();
    }

    @ThreadScopeBean
    public ContextContainer<String> getContextContainerString() {
        return new ContextContainer<String>();
    }

    @ThreadScopeBean
    public ContextContainer<Integer> getContextContainerInt() {
        return new ContextContainer<Integer>();
    }
}
