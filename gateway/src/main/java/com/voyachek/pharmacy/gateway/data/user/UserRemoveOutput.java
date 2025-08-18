package com.voyachek.pharmacy.gateway.data.user;

import lombok.Data;

/**
 * Результат удаления пользователя
 */
@Data
public class UserRemoveOutput {

    /**
     * Идентификатор записи
     */
    private String id;
    /**
     * Имя пользователя
     */
    private String name;
}
