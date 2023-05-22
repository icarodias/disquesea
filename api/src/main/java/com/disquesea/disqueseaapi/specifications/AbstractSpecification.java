package com.disquesea.disqueseaapi.specifications;

import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static java.util.Objects.isNull;

public abstract class AbstractSpecification<T> {

    public static <T> Specification<T> attributeContains(String value, String attributeName) {
        if (isNull(value)) {
            return null;
        }

        return (root, query, builder) -> builder.like(
                builder.lower(root.get(attributeName).as(String.class)), "%" + value.toLowerCase() + "%");
    }

    public static <T> Specification<T> attributeBooleanIs(Boolean value, String attributeName) {
        if (isNull(value)) {
            return null;
        }

        return (root, query, builder) -> builder.equal(root.get(attributeName).as(Boolean.class), value);
    }

    public static <T> Specification<T> attributeEnumIs(Enum<?> value, String attributeName) {
        if (isNull(value)) {
            return null;
        }

        return (root, query, builder) -> builder.equal(root.get(attributeName), value);
    }

    public static <T> Specification<T> dateIsGranterThanOrEqualTo(LocalDate value, String attributeName) {
        if (isNull(value)) {
            return null;
        }

        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(attributeName).as(LocalDate.class), value);
    }

    public static <T> Specification<T> dateIsLessThanOrEqualTo(LocalDate value, String attributeName) {
        if (isNull(value)) {
            return null;
        }

        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(attributeName).as(LocalDate.class), value);
    }

    public static <T> Specification<T> composedIdAttributeIs(Long value, String attributeName) {
        if (isNull(value)) {
            return null;
        }

        return ((root, query, builder) -> {
            From<?, ?> compositeAttributeJoin = root.join(attributeName, JoinType.INNER);
            return builder.equal(compositeAttributeJoin.get("id").as(Long.class), value);
        });
    }

    public static <T> Specification<T> composedEnumIs(Enum<?> value, String attributeName, String composedEnumName) {
        if (isNull(value)) {
            return null;
        }

        return ((root, query, builder) -> {
            From<?, ?> compositeAttributeJoin = root.join(attributeName, JoinType.INNER);
            return builder.equal(compositeAttributeJoin.get(composedEnumName), value);
        });
    }
}
