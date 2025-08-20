package com.voyachek.pharmacy.medication.repository;

import com.voyachek.pharmacy.medication.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для сущности "Препарат"
 */
@Repository
public interface MedicationRepository extends JpaRepository<Medication, UUID> {
}
