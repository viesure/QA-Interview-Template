package com.viesure.coding.challenge.selenium.framework.context;

import java.util.HashMap;
import java.util.Map;

public class ContextContainer<T> {

    private Map<ContextKeys, T> map = new HashMap<>();

    public Map<ContextKeys, T> getMap() {
        return map;
    }
}
