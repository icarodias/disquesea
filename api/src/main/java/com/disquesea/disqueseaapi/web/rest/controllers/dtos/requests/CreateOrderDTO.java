package com.disquesea.disqueseaapi.web.rest.controllers.dtos.requests;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class CreateOrderDTO {

    @Positive
    private BigDecimal amount;

    @Positive
    private BigDecimal price;

    @NonNull
    private Boolean isSell;

    private String description;

    private long productId;

}
