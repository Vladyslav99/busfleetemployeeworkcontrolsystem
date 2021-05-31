package com.kondratenko.busparkemploeesworkcontrol.utils;

import java.time.LocalDateTime;

public class DateTimeUtils {

    private DateTimeUtils() {

    }

    public static boolean isDateTimeAfterNow(LocalDateTime dateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return dateTime.isAfter(currentDateTime);
    }
}
