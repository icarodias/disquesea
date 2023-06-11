package com.disquesea.disqueseaapi.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;

public abstract class DateCustomUtils {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final DateTimeFormatter DATE_FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDate fromString(String stringDate) {
        return isNull(stringDate) ? null : LocalDate.parse(stringDate, DATE_FORMAT);
    }

}
