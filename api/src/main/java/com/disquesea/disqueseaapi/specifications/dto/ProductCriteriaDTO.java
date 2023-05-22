package com.disquesea.disqueseaapi.specifications.dto;

import com.disquesea.disqueseaapi.domain.model.enums.Category;
import com.disquesea.disqueseaapi.domain.model.enums.Scale;
import com.disquesea.disqueseaapi.domain.model.enums.Status;
import lombok.Data;

@Data
public class ProductCriteriaDTO {

    private String name;

    private Scale scale;

    private Status status;

    private Category category;

    private Boolean isVisibleInCatalog;

}
