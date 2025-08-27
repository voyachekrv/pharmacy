package com.voyachek.pharmacy.medication.specification.base.reference;

import com.voyachek.pharmacy.medication.entity.base.BaseReference;
import org.springframework.data.jpa.domain.Specification;

public class DefaultReferenceSpecifications {

    public static <T extends BaseReference> Specification<T> nameLike(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}
