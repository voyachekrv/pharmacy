package com.voyachek.pharmacy.medication.entity.pkey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.util.UUID;

/**
 * Первичный ключ связующей сущности "Связь препарата и его категории"
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MedicationCategoryPKey implements Serializable {

    /**
     * Идентификатор препарата
     */
    @Column(name = "medication_id")
    @Comment("Идентификатор препарата")
    private UUID medicationId;

    /**
     * Идентификатор категории
     */
    @Column(name = "ref_category_id")
    @Comment("Идентификатор категории")
    private UUID refCategoryId;
}
