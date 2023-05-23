package com.disquesea.disqueseaapi.web.rest.controllers.dtos.requests;

import com.disquesea.disqueseaapi.domain.model.enums.Category;
import com.disquesea.disqueseaapi.domain.model.enums.Scale;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductDTO {

    @NotBlank
    private String name;

    @PositiveOrZero
    private BigDecimal amount;

    @Positive
    private BigDecimal price;

    private Boolean isVisibleInCatalog;

    @NotNull
    private Scale scale;

    @NotNull
    private Category category;

}
