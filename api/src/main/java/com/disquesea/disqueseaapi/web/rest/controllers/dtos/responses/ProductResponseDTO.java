package com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses;

import com.disquesea.disqueseaapi.domain.model.enums.Category;
import com.disquesea.disqueseaapi.domain.model.enums.Scale;
import com.disquesea.disqueseaapi.domain.model.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {

    private Long id;

    private String name;

    protected BigDecimal amount;

    private BigDecimal price;

    private Boolean isVisibleInCatalog;

    private Category category;

    private Scale scale;

    private Status status;

}
