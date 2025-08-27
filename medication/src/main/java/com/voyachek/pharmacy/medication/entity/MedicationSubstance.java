package com.voyachek.pharmacy.medication.entity;

import com.voyachek.pharmacy.medication.entity.pkey.MedicationSubstancePKey;
import com.voyachek.pharmacy.medication.entity.reference.RefSubstance;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

/**
 * Связь препарата и его действующего вещества
 */
@Entity
@Table(name = "medication_substance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationSubstance {

    /**
     * Первичный ключ
     */
    @EmbeddedId
    @Builder.Default
    private MedicationSubstancePKey id = new MedicationSubstancePKey();

    /**
     * Препарат
     */
    @ManyToOne
    @MapsId("medicationId")
    @JoinColumn(name = "medication_id")
    private Medication medication;

    /**
     * Действующее вещество
     */
    @ManyToOne
    @MapsId("refSubstanceId")
    @JoinColumn(name = "ref_substance_id")
    private RefSubstance refSubstance;

    /**
     * Концентрация действующего вещества
     */
    @Column(name = "concentration", nullable = false)
    @Comment("Концентрация действующего вещества")
    @EqualsAndHashCode.Include
    private BigDecimal concentration;
}
