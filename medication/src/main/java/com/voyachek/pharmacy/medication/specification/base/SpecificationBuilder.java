package com.voyachek.pharmacy.medication.specification.base;

import com.voyachek.pharmacy.medication.entity.base.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class SpecificationBuilder<T extends BaseEntity> {

    private final List<Specification<T>> specifications = new ArrayList<>();

    public SpecificationBuilder<T> and(Specification<T> spec) {
        if (spec != null) {
            specifications.add(spec);
        }
        return this;
    }

    public SpecificationBuilder<T> andIf(boolean condition, Function<Void, Specification<T>> supplier) {
        if (condition) {
            specifications.add(supplier.apply(null));
        }
        return this;
    }

    public SpecificationBuilder<T> or(Specification<T> spec) {
        if (spec != null) {
            if (specifications.isEmpty()) {
                specifications.add(spec);
            } else {
                Specification<T> last = specifications.remove(specifications.size() - 1);
                specifications.add(last.or(spec));
            }
        }
        return this;
    }

    public Specification<T> build() {
        return specifications.stream()
                .reduce(Specification::and)
                .orElse(null);
    }
}
