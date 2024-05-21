package com.viesure.coding.challenge.selenium.framework.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.SimpleThreadScope;

public class ThreadScope extends SimpleThreadScope {

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object o = super.get(name, objectFactory);

        return o;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
    }
}
