package com.voyachek.pharmacy.medication.entity;

import com.voyachek.pharmacy.medication.entity.pkey.MedicationCategoryPKey;
import com.voyachek.pharmacy.medication.entity.reference.RefCategory;
import jakarta.persistence.*;
import lombok.*;

/**
 * Связь препарата и его категории
 */
@Entity
@Table(name = "medication_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationCategory {

    /**
     * Первичный ключ
     */
    @EmbeddedId
    @Builder.Default
    private MedicationCategoryPKey id = new MedicationCategoryPKey();

    /**
     * Препарат
     */
    @ManyToOne
    @MapsId("medicationId")
    @JoinColumn(name = "medication_id")
    private Medication medication;

    /**
     * Категория препарата
     */
    @ManyToOne
    @MapsId("refCategoryId")
    @JoinColumn(name = "ref_category_id")
    private RefCategory category;
}
