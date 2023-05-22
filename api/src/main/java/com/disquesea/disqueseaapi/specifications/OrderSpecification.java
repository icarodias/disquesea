package com.disquesea.disqueseaapi.specifications;

import com.disquesea.disqueseaapi.domain.model.Order;
import com.disquesea.disqueseaapi.domain.model.enums.Category;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class OrderSpecification {

    public static Specification<Order> sellIs(Boolean isSell) {
        return AbstractSpecification.attributeBooleanIs(isSell, "isSell");
    }

    public static Specification<Order> createdAfter(LocalDate date) {
        return AbstractSpecification.dateIsGranterThanOrEqualTo(date, "createdAt");
    }

    public static Specification<Order> createdBefore(LocalDate date) {
        return AbstractSpecification.dateIsLessThanOrEqualTo(date, "createdAt");
    }

    public static Specification<Order> productIdIs(Long id) {
        return AbstractSpecification.composedIdAttributeIs(id, "product");
    }

    public static Specification<Order> productCategoryIs(Category category) {
        return AbstractSpecification.composedEnumIs(category, "product", "category");
    }


}
