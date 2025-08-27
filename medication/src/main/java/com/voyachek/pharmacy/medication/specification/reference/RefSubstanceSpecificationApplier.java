package com.voyachek.pharmacy.medication.specification.reference;

import com.voyachek.pharmacy.medication.entity.reference.RefSubstance;
import com.voyachek.pharmacy.medication.specification.base.reference.DefaultReferenceSpecificationApplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefSubstanceSpecificationApplier extends DefaultReferenceSpecificationApplier<RefSubstance> {}
