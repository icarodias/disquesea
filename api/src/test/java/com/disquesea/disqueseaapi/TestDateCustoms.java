package com.disquesea.disqueseaapi;

import com.disquesea.disqueseaapi.components.DateCustomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TestDateCustoms {
    @Test
    public void DateFormatTest() {
        Assertions.assertEquals(LocalDate.now().format(DateCustomUtils.DATE_FILE_NAME_FORMAT).toString(), "06-06-2023");
    }
}
