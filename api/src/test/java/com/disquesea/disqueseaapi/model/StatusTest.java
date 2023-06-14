package com.disquesea.disqueseaapi.model;

import com.disquesea.disqueseaapi.domain.model.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class StatusTest {

    @Test
    public void getCorrectStatusTest() {
        //scenario
        BigDecimal amount1 = BigDecimal.TEN;
        BigDecimal amount2 = BigDecimal.valueOf(5.001);

        BigDecimal amount3 = BigDecimal.valueOf(5);
        BigDecimal amount4 = BigDecimal.valueOf(4.999);
        BigDecimal amount5 = BigDecimal.ONE;
        BigDecimal amount6 = BigDecimal.valueOf(0.001);

        BigDecimal amount7 = BigDecimal.ZERO;
        BigDecimal amount8 = BigDecimal.valueOf(-0.001);
        BigDecimal amount9 = BigDecimal.valueOf(-10.001);

        //actions - no actions - static method

        //assertions
        Assertions.assertEquals(Status.getCorrectStatus(amount1),Status.AVAILABLE);
        Assertions.assertEquals(Status.getCorrectStatus(amount2),Status.AVAILABLE);

        Assertions.assertEquals(Status.getCorrectStatus(amount3),Status.CRITICAL);
        Assertions.assertEquals(Status.getCorrectStatus(amount4),Status.CRITICAL);
        Assertions.assertEquals(Status.getCorrectStatus(amount5),Status.CRITICAL);
        Assertions.assertEquals(Status.getCorrectStatus(amount6),Status.CRITICAL);

        Assertions.assertEquals(Status.getCorrectStatus(amount7),Status.ABSENT);
        Assertions.assertEquals(Status.getCorrectStatus(amount8),Status.ABSENT);
        Assertions.assertEquals(Status.getCorrectStatus(amount9),Status.ABSENT);

    }
}
