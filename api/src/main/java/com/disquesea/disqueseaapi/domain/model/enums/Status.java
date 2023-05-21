package com.disquesea.disqueseaapi.domain.model.enums;

import java.math.BigDecimal;

public enum Status {
    AVAILABLE("disponível", BigDecimal.valueOf(Double.MAX_VALUE)),
    CRITICAL("crítico", BigDecimal.valueOf(5L)),
    ABSENT("indisponível", BigDecimal.ZERO);

    private final String description;

    private final BigDecimal limitValue;

    Status(String description, BigDecimal limitValue) {
        this.description = description;
        this.limitValue = limitValue;
    }

    public String getDescription(){
        return description;
    }

    public BigDecimal getLimitValue() { return limitValue;}

    public static Status getCorrectStatus(BigDecimal amount) {
        if (amount.compareTo(ABSENT.getLimitValue()) != 1) {
            return ABSENT;
        } else if (amount.compareTo(CRITICAL.getLimitValue()) != 1) {
            return CRITICAL;
        } else {
            return AVAILABLE;
        }
    }
}
