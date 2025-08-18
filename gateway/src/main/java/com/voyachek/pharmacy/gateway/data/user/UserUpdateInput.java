package com.voyachek.pharmacy.gateway.data.user;

import lombok.Data;

/**
 * Входные данные для обновления пользователя
 */
@Data
public class UserUpdateInput {

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
}
