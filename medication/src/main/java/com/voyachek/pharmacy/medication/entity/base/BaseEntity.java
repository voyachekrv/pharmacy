package com.voyachek.pharmacy.medication.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Базовые поля для сущности с ID, датой создания и обновления
 */
@Getter
@Setter
@ToString
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity implements Serializable {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @Column(name = "id", unique = true, nullable = false)
    @ColumnDefault("gen_random_uuid()")
    @Comment("Идентификатор записи")
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id;

    /**
     * Дата/время создания записи
     */
    @Column(name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    @Comment("Дата/время создания записи")
    protected LocalDateTime createdAt;

    /**
     * Дата/время модификации записи
     */
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @LastModifiedDate
    @Comment("Дата/время модификации записи")
    protected LocalDateTime updatedAt;
}
