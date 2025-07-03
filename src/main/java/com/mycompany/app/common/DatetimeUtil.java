package com.mycompany.app.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DatetimeUtil {

    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);

    public static LocalDateTime toDatetime(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    public static LocalDate toDate(String date) {
        String dateTime = date + " 00:00:00";
        return toDatetime(dateTime).toLocalDate();
    }

    public static LocalTime toTime(String time) {
        String dateTime = "2000-01-01 " + time;
        return toDatetime(dateTime).toLocalTime();
    }
}
