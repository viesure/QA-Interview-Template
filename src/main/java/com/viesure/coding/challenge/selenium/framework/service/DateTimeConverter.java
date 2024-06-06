package com.viesure.coding.challenge.selenium.framework.service;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class DateTimeConverter {

    /**
     * Convert current time with time zone
     *
     * @param timeZone
     * @return current time in the given time zone
     */
    public String getCurrentTimeWithTimeZone(String timeZone) {
        DateTime localDateTime = new DateTime();
        DateTimeZone sydneyTimeZone = DateTimeZone.forID(timeZone);

        DateTime sydneyDateTime = localDateTime.withZone(sydneyTimeZone);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mma");

        String formattedSydneyDateTime = formatter.print(sydneyDateTime);
        return formattedSydneyDateTime.toLowerCase();

    }

}
