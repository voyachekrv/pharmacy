package com.voyachek.pharmacy.user.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.util.UUID;

/**
 * Идентифицируемая запись в БД
 */
@Getter
@Setter
@ToString
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Identity implements Serializable {

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
}
