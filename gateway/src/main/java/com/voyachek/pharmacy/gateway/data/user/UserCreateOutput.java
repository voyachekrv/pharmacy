package com.voyachek.pharmacy.gateway.data.user;

import lombok.Builder;
import lombok.Data;

/**
 * Выходные данные для создания пользователя
 */
@Data
@Builder
public class UserCreateOutput {

    /**
     * Идентификатор записи
     */
    protected String id;
}
