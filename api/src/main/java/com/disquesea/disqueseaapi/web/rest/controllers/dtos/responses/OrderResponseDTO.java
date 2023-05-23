package com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrderResponseDTO {

    private Long id;

    private BigDecimal amount;

    private BigDecimal price;

    private Boolean isSell;

    private LocalDate createdAt;

    private String description;

    private ProductShortResponseDTO product;

}
