package com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses;

import com.disquesea.disqueseaapi.domain.model.enums.Category;
import com.disquesea.disqueseaapi.domain.model.enums.Scale;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductShortResponseDTO {

    private String name;

    private BigDecimal price;

    private Category category;

    private Scale scale;

}
