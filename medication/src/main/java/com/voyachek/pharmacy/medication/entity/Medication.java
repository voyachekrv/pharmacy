package com.voyachek.pharmacy.medication.entity;

import com.voyachek.pharmacy.medication.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Препарат
 */
@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "medication")
@EntityListeners(AuditingEntityListener.class)
public class Medication extends BaseEntity {

    /**
     * Наименование препарата
     */
    @Column(name = "name", nullable = false, columnDefinition = "varchar")
    @Comment("Наименование")
    @EqualsAndHashCode.Include
    private String name;

    /**
     * Текущая цена препарата
     */
    @Column(name = "price", nullable = false)
    @Comment("Предыдущая цена препарата")
    @EqualsAndHashCode.Include
    private BigDecimal price;

    /**
     * Текущая цена препарата
     */
    @Column(name = "old_price")
    @Comment("Предыдущая цена препарата")
    @EqualsAndHashCode.Include
    private BigDecimal oldPrice;

    /**
     * Отпускается ли препарат только по рецепту
     */
    @Column(name = "is_prescription_only")
    @Comment("Предыдущая цена препарата")
    @EqualsAndHashCode.Include
    private boolean isPrescriptionOnly;

    /**
     * Категории препарата
     */
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Категории препарата")
    @EqualsAndHashCode.Include
    @Builder.Default
    private Set<MedicationCategory> medicationCategories = new HashSet<>();

    /**
     * Действующие вещества препарата
     */
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Действующие вещества препарата")
    @EqualsAndHashCode.Include
    @Builder.Default
    private Set<MedicationSubstance> activeSubstances = new HashSet<>();
}
