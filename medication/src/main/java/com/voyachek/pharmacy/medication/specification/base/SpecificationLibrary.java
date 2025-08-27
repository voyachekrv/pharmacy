package com.voyachek.pharmacy.medication.specification.base;

import com.voyachek.pharmacy.medication.entity.base.BaseEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public final class SpecificationLibrary {

    public static <T extends BaseEntity> Specification<T> like(String fieldName, String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%");
        };
    }

    public static <T extends BaseEntity> Specification<T> isTrue(String fieldName) {
        return (root, query, cb) -> cb.isTrue(root.get(fieldName));
    }

    public static <T extends BaseEntity> Specification<T> isFalse(String fieldName) {
        return (root, query, cb) -> cb.isFalse(root.get(fieldName));
    }

    public static <T extends BaseEntity> Specification<T> equal(String fieldName, boolean value) {
        return (root, query, cb) -> cb.equal(root.get(fieldName), value);
    }
}
