package com.voyachek.pharmacy.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import com.voyachek.pharmacy.grpclib.user.UserRoleEnum;

/**
 * Роль пользователя в системе
 */
@Getter
@RequiredArgsConstructor
@ToString
public enum UserRole {
    /**
     * Работник системы заказов
     */
    EMPLOYEE("EMPLOYEE", 0),

    /**
     * Покупатель
     */
    CUSTOMER("CUSTOMER", 1);

    /**
     * Название
     */
    private final String name;

    /**
     * Индекс
     */
    private final int index;

    /**
     * Создание роли из {@link UserRoleEnum}
     * @param index Индекс роли
     * @return {@link UserRole}
     */
    public static UserRole fromProtobuf(int index) {
        return UserRole.values()[index];
    }
}
