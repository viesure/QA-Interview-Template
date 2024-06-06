package com.viesure.coding.challenge.selenium.framework.aop;

import com.viesure.coding.challenge.selenium.framework.annotation.TakeScreenshot;
import com.viesure.coding.challenge.selenium.framework.service.ScreenshotService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
public class ScreenshotAspect {

    @Autowired
    private ScreenshotService screenshotService;

    @After("@annotation(takeScreenshot)")
    public void after(TakeScreenshot takeScreenshot) throws IOException {
        this.screenshotService.takeScreenShot();
    }

}
