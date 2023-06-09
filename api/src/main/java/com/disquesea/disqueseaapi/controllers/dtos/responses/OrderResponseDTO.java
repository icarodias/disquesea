package com.disquesea.disqueseaapi.controllers.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrderResponseDTO {

    private Long id;

    private BigDecimal amount;

    private BigDecimal price;

    private Boolean isSell;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    private ProductShortResponseDTO product;

}
