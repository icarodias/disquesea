package com.disquesea.disqueseaapi.specifications.dto;

import com.disquesea.disqueseaapi.domain.model.enums.Category;
import com.disquesea.disqueseaapi.domain.validations.annotations.DateConstraint;
import lombok.Data;

@Data
public class OrderCriteriaDTO {

    private Boolean isSell;

    @DateConstraint
    private String fromDate;

    @DateConstraint
    private String toDate;

    private Long productId;

    private Category productCategory;

}
