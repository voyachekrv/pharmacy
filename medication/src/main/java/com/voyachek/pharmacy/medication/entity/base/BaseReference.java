package com.voyachek.pharmacy.medication.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

/**
 * Базовый класс для создания справочника
 */
@ToString
@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseReference extends BaseEntity {

    /**
     * Ключ записи
     */
    @Column(name = "key", nullable = false)
    @Comment("Ключ записи")
    @EqualsAndHashCode.Include
    private String key;

    /**
     * Наименование
     */
    @Column(name = "name", nullable = false, columnDefinition = "varchar")
    @Comment("Наименование")
    @EqualsAndHashCode.Include
    private String name;

    /**
     * Порядковый номер
     */
    @Column(name = "ordering", nullable = false)
    @Comment("Порядковый номер")
    @EqualsAndHashCode.Include
    private Integer ordering;
}
