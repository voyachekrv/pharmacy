package com.voyachek.pharmacy.user.service;

import com.voyachek.pharmacy.grpclib.user.*;
import com.voyachek.pharmacy.grpclib.utils.page.PageUtil;
import com.voyachek.pharmacy.user.entity.User;

/**
 * Интерфейс сервиса для работы с пользователем ({@link User})
 */
public interface UserService {
    /**
     * Добавление нового пользователя
     * @param request Контракт на создание пользователя ({@link UserCreateContract.UserCreateRequest})
     * @return {@link UserCreateContract.UserCreateResponse}
     */
    UserCreateContract.UserCreateResponse createUser(UserCreateContract.UserCreateRequest request);

    /**
     * Обновление пользователя
     * @param request Контракт на обновление пользователя ({@link UserUpdateContract.UserUpdateRequest})
     * @return {@link UserUpdateContract.UserUpdateResponse}
     */
    UserUpdateContract.UserUpdateResponse updateUser(UserUpdateContract.UserUpdateRequest request);

    /**
     * Удаление пользователя
     * @param request Контракт на удаление пользователя ({@link UserRemoveContract.UserRemoveRequest})
     * @return {@link UserRemoveContract.UserRemoveResponse}
     */
    UserRemoveContract.UserRemoveResponse removeUser(UserRemoveContract.UserRemoveRequest request);

    /**
     * Поиск пользователя по его ID
     * @param request Контракт на поиск пользователя ({@link UserFindByIdContract.UserFindByIdRequest})
     * @return {@link UserFindByIdContract.UserFindByIdResponse}
     */
    UserFindByIdContract.UserFindByIdResponse findById(UserFindByIdContract.UserFindByIdRequest request);

    /**
     * Постраничный поиск списка пользователей
     * @param request Контракт на запрос страницы ({@link PageUtil.PageRequest})
     * @return {@link UserFindAllContract.UserFindAllResponse}
     */
    UserFindAllContract.UserFindAllResponse findAll(PageUtil.PageRequest request);
}
