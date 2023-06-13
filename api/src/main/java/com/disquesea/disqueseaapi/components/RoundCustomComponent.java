package com.disquesea.disqueseaapi.components;

import com.disquesea.disqueseaapi.domain.model.enums.Scale;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class RoundCustomComponent {

    public static BigDecimal roundingAmount(BigDecimal value, Scale scale) {
        final boolean isKilogramScale = scale.equals(Scale.KILOGRAM);

        return (isKilogramScale) ? value.setScale(3, RoundingMode.HALF_UP)
                : value.setScale(0, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal roundPrice(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

}
