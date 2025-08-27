package com.voyachek.pharmacy.medication.repository;

import com.voyachek.pharmacy.medication.entity.Medication;
import com.voyachek.pharmacy.medication.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для сущности "Препарат"
 */
@Repository
public interface MedicationRepository extends BaseRepository<Medication> {
    @Override
    @EntityGraph(attributePaths = {
            "medicationCategories.category",
            "activeSubstances.refSubstance"
    })
    Page<Medication> findAll(Specification<Medication> spec, Pageable pageable);
}
