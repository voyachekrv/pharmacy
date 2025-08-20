package com.voyachek.pharmacy.medication.entity.pkey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.util.UUID;

/**
 * Первичный ключ связующей сущности "Связь препарата и его действующего вещества"
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MedicationSubstancePKey implements Serializable {

    /**
     * Идентификатор препарата
     */
    @Column(name = "medication_id")
    @Comment("Идентификатор препарата")
    private UUID medicationId;

    /**
     * Идентификатор действующего вещества
     */
    @Column(name = "ref_substance_id")
    @Comment("Идентификатор действующего вещества")
    private UUID refSubstanceId;
}
