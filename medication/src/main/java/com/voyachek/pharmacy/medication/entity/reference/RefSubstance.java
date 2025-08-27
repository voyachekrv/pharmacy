package com.voyachek.pharmacy.medication.entity.reference;

import com.voyachek.pharmacy.medication.entity.base.BaseReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Действующее вещество
 */
@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "ref_substance", uniqueConstraints = {@UniqueConstraint(name = "ref_substance_key_uk", columnNames = "key")})
public class RefSubstance extends BaseReference {}
