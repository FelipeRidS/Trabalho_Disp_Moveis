package com.udesc.myapplication.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateHelpers {
    public static String format(LocalDate date) {
        DateTimeFormatter mediumFormatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(Locale.getDefault());

        return date.format(mediumFormatter);
    }
}
