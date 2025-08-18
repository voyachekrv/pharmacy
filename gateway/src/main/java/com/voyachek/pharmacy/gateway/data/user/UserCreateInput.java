package com.voyachek.pharmacy.gateway.data.user;

import lombok.Data;

/**
 * Входные данные для создания пользователя
 */
@Data
public class UserCreateInput {

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
}
