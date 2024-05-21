package com.viesure.coding.challenge.selenium.framework.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Lazy
@Service
public class StringService {

    /**
     * Convert given string to file name friendly string with current date
     *
     * @param fileName file name tobe converted
     * @return file name with whitespaces replaced with _ and date as postfix
     */
    public String convertScenarioNameToFileNameWithDate(String fileName) {

        if (fileName == null || fileName.isEmpty()) {
            throw new RuntimeException("File name is empty!");
        }
        final String extension = ".mp4";

        String scenarioNameFileNameFriendly = fileName.replaceAll("\\s", "_").toLowerCase();


        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy-hh-mm-ss-SSS");
        String strDate = formatter.format(date);

        String videoFileNameWithDate = scenarioNameFileNameFriendly + "-" + strDate + extension;
        return videoFileNameWithDate;

    }

}
