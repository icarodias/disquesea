package com.disquesea.disqueseaapi.specifications;

import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.model.enums.Category;
import com.disquesea.disqueseaapi.domain.model.enums.Scale;
import com.disquesea.disqueseaapi.domain.model.enums.Status;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> nameContains(String name) {
        return AbstractSpecification.attributeContains(name, "name");
    }

    public static Specification<Product> scaleIs(Scale scale) {
        return AbstractSpecification.attributeEnumIs(scale, "scale");
    }

    public static Specification<Product> statusIs(Status status) {
        return AbstractSpecification.attributeEnumIs(status, "status");
    }

    public static Specification<Product> categoryIs(Category category) {
        return AbstractSpecification.attributeEnumIs(category, "category");
    }

    public static Specification<Product> catalogVisibility(Boolean isVisibleInCatalog) {
        return AbstractSpecification.attributeBooleanIs(isVisibleInCatalog, "isVisibleInCatalog");
    }

}
