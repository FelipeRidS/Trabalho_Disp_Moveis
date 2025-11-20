package com.udesc.myapplication.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateHelpers {
    public static String format(LocalDate date) {
        DateTimeFormatter mediumFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return date.format(mediumFormatter);
    }

    public static String format(LocalDateTime date) {
        DateTimeFormatter mediumFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return date.format(mediumFormatter);
    }

    public static LocalDate parse(String date) {
        DateTimeFormatter mediumFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(date, mediumFormatter);
    }
}
