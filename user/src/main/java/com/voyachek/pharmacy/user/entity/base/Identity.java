package com.voyachek.pharmacy.user.entity.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
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
