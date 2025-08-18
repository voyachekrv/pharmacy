package com.voyachek.pharmacy.gateway.data.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    /**
     * Идентификатор записи
     */
    private String id;
    /**
     * Имя пользователя
     */
    private String name;
    /**
     * Email пользователя
     */
    private String email;
    /**
     * Роль пользователя в системе
     */
    private UserRole role;
    /**
     * Дата регистрации в системе
     */
    private String registrationDate;
}
