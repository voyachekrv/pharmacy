package com.voyachek.pharmacy.user.entity;

import com.voyachek.pharmacy.user.entity.base.Identity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Пользователь
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"user\"", schema = "\"user\"")
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class User extends Identity {

    /**
     * Имя пользователя
     */
    @Column(name = "name")
    @Comment("Имя пользователя")
    @EqualsAndHashCode.Include
    @ToString.Include
    private String name;

    /**
     * Email пользователя
     */
    @Column(name = "email")
    @Comment("Email пользователя")
    @EqualsAndHashCode.Include
    @ToString.Include
    private String email;

    /**
     * Роль пользователя в системе
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Comment("Роль пользователя в системе")
    @EqualsAndHashCode.Include
    @ToString.Include
    private UserRole role;

    /**
     * Дата/время создания записи
     */
    @Column(name = "registration_date",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    @Comment("Дата/время регистрации пользователя")
    protected LocalDateTime registrationDate;
}
