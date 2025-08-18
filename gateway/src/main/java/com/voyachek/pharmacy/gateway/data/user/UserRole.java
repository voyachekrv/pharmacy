package com.voyachek.pharmacy.gateway.data.user;

import com.voyachek.pharmacy.grpclib.user.UserRoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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
    EMPLOYEE("EMPLOYEE"),

    /**
     * Покупатель
     */
    CUSTOMER("CUSTOMER");

    /**
     * Название
     */
    private final String name;

    /**
     * Создание роли из {@link UserRole}
     * @return {@link UserRoleEnum}
     */
    public UserRoleEnum.UserRole toProtobuf() {
        return UserRoleEnum.UserRole.valueOf(this.name);
    }
}
