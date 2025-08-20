package com.voyachek.pharmacy.medication.entity.reference;

import com.voyachek.pharmacy.medication.entity.base.BaseReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Категория препарата
 */
@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "ref_category", uniqueConstraints = {@UniqueConstraint(name = "ref_category_key_uk", columnNames = "key")})
public class RefCategory extends BaseReference {}
