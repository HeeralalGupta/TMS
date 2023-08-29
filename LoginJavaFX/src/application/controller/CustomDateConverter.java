package application.controller;

import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDateConverter extends StringConverter<LocalDate> {
    private final DateTimeFormatter dateFormatter;

    public CustomDateConverter(String pattern) {
        dateFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public String toString(LocalDate date) {
        if (date != null) {
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    @Override
    public LocalDate fromString(String string) {
        if (string != null && !string.trim().isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }
}
