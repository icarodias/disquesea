package com.disquesea.disqueseaapi;

import com.disquesea.disqueseaapi.components.DateCustomComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TestDateCustoms {
    @Test
    public void DateFormatTest() {
        LocalDate date = LocalDate.of(2023, 02, 18);
        Assertions.assertEquals(date.format(DateCustomComponent.DATE_FILE_NAME_FORMAT).toString(), "18-02-2023");
        Assertions.assertEquals(date.format(DateCustomComponent.DATE_FORMAT).toString(), "18/02/2023");
    }
}
