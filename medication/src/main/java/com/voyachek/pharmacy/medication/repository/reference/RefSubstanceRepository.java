package com.voyachek.pharmacy.medication.repository.reference;

import com.voyachek.pharmacy.medication.entity.reference.RefSubstance;
import com.voyachek.pharmacy.medication.repository.base.reference.BaseReferenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для справочника "Действующее вещество"
 */
@Repository
public interface RefSubstanceRepository extends BaseReferenceRepository<RefSubstance> {
    long countByKeyIn(List<String> keys);
}
