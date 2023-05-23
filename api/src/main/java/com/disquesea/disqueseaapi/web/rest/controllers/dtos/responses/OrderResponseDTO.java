package com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses;

import com.disquesea.disqueseaapi.domain.model.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrderResponseDTO {

    private Long id;

    private BigDecimal amount;

    private BigDecimal price;

    private LocalDate createdAt;

    private Boolean isSell;

    private String description;

    private ProductShortResponseDTO product;

}
