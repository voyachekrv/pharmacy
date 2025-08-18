package com.voyachek.pharmacy.gateway.data.user;

import lombok.Data;

/**
 * Выходные данные для обновленияо пользователя
 */
@Data
public class UserUpdateOutput {

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
