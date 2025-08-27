package com.voyachek.pharmacy.medication.specification.base.reference;

import com.voyachek.pharmacy.grpclib.utils.reference.DefaultReferenceUtil;
import com.voyachek.pharmacy.medication.entity.base.BaseReference;
import com.voyachek.pharmacy.medication.specification.base.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

public abstract class DefaultReferenceSpecificationApplier<T extends BaseReference> {

    public Specification<T> applyDefault(DefaultReferenceUtil.DefaultReferenceQuery request) {
        return new SpecificationBuilder<T>()
                .and(DefaultReferenceSpecifications.nameLike(request.getName()))
                .build();
    }
}
