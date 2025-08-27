package com.voyachek.pharmacy.medication.specification;

import com.voyachek.pharmacy.grpclib.medication.MedicationFindAllContract;
import com.voyachek.pharmacy.medication.entity.Medication;
import com.voyachek.pharmacy.medication.entity.MedicationCategory;
import com.voyachek.pharmacy.medication.entity.MedicationSubstance;
import com.voyachek.pharmacy.medication.entity.reference.RefCategory;
import com.voyachek.pharmacy.medication.entity.reference.RefSubstance;
import com.voyachek.pharmacy.medication.specification.base.SpecificationBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.voyachek.pharmacy.medication.specification.base.SpecificationLibrary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MedicationSpecificationApplier {

    private static final String CONCENTRATION = "concentration";

    public Specification<Medication> apply(MedicationFindAllContract.MedicationFindAllFilterRequest request) {
        var builder = new SpecificationBuilder<Medication>();

        if (request.hasIsPrescriptionOnly()) {
            builder.and(SpecificationLibrary.equal("isPrescriptionOnly", request.getIsPrescriptionOnly()));
        }

        if (request.getCategoriesCount() > 0) {
            builder.and(hasCategoriesNames(request));
        }

        if (request.hasActiveSubstance()) {
            builder.and(hasActiveSubstance(request.getActiveSubstance()));
        }

        return builder.build();
    }

    private Specification<Medication> hasCategoriesNames(MedicationFindAllContract.MedicationFindAllFilterRequest request) {
        return (root, query, cb) -> {
            Join<Medication, MedicationCategory> categoryJoin =
                    root.join("medicationCategories", JoinType.INNER);

            Join<MedicationCategory, RefCategory> refCategoryJoin =
                    categoryJoin.join("category", JoinType.INNER);

            return refCategoryJoin.get("name").in(request.getCategoriesList().stream().toList());
        };
    }

    private Specification<Medication> hasActiveSubstance(MedicationFindAllContract.MedicationFindAllActiveSubstanceRequest filter) {
        return (root, query, cb) -> {
            Join<Medication, MedicationSubstance> substanceJoin =
                    root.join("activeSubstances", JoinType.INNER);
            Join<MedicationSubstance, RefSubstance> refSubstanceJoin =
                    substanceJoin.join("refSubstance", JoinType.INNER);

            List<Predicate> predicates = new ArrayList<>();

            if (!filter.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(refSubstanceJoin.get("name")),
                                "%" + filter.getName().toLowerCase() + "%"));
            }

            if (filter.hasConcentration()) {
                var concentration = filter.getConcentration();
                if (greaterThanZero(concentration.getLt())) {
                    predicates.add(cb.lt(substanceJoin.get(CONCENTRATION),
                            BigDecimal.valueOf(concentration.getLt())));
                }
                if (greaterThanZero(concentration.getGt())) {
                    predicates.add(cb.gt(substanceJoin.get(CONCENTRATION),
                            BigDecimal.valueOf(concentration.getGt())));
                }
                if (greaterThanZero(concentration.getLte())) {
                    predicates.add(cb.le(substanceJoin.get(CONCENTRATION),
                            BigDecimal.valueOf(concentration.getLte())));
                }
                if (greaterThanZero(concentration.getGte())) {
                    predicates.add(cb.ge(substanceJoin.get(CONCENTRATION),
                            BigDecimal.valueOf(concentration.getGte())));
                }
                if (greaterThanZero(concentration.getEq())) {
                    predicates.add(cb.equal(substanceJoin.get(CONCENTRATION),
                            BigDecimal.valueOf(concentration.getEq())));
                }
                if (greaterThanZero(concentration.getNeq())) {
                    predicates.add(cb.notEqual(substanceJoin.get(CONCENTRATION),
                            BigDecimal.valueOf(concentration.getNeq())));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private boolean greaterThanZero(long value) {
        return value > 0;
    }
}
