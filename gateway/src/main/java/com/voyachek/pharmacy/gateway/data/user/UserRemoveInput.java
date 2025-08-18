package com.voyachek.pharmacy.gateway.data.user;

import lombok.Data;

/**
 * Входные данные для удаления пользователя
 */
@Data
public class UserRemoveInput {

    /**
     * Идентификатор записи
     */
    private String id;
}
