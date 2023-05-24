package com.disquesea.disqueseaapi.web.rest.controllers.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderDTO {

    @Positive
    @NotNull
    private BigDecimal amount;

    @Positive
    private BigDecimal price;

    @NotNull
    private Boolean isSell;

    private String description;

    @NotNull
    private long productId;

}
