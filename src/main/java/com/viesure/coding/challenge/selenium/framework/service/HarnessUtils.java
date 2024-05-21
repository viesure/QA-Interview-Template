package com.viesure.coding.challenge.selenium.framework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class HarnessUtils {
    @Value("${default.wait:3000}")
    private static long wait;

    public static void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Sleep interrupted: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void waitFor() {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            log.error("Sleep interrupted: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

