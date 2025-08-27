package com.voyachek.pharmacy.gateway.graphql.user;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.voyachek.pharmacy.gateway.data.user.*;
import com.voyachek.pharmacy.gateway.data.utils.Page;
import com.voyachek.pharmacy.gateway.data.utils.PageRequest;
import com.voyachek.pharmacy.gateway.mapper.page.PaginationMapper;
import com.voyachek.pharmacy.gateway.mapper.user.UserMapper;
import com.voyachek.pharmacy.grpclib.user.UserEndpointGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * GraphQL-компонент для работы с сервисом пользователей
 */
@DgsComponent
@ConditionalOnProperty(name = "app.services.user.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class UserGraphQLComponent {

    private final UserEndpointGrpc.UserEndpointBlockingStub userEndpointBlockingStub;
    private final UserMapper userMapper;
    private final PaginationMapper paginationMapper;

    /**
     * Создание нового пользователя
     * @param input Входные данные для создания {@link UserCreateInput}
     * @return {@link UserCreateOutput}
     */
    @DgsMutation
    public UserCreateOutput createUser(UserCreateInput input) {
        log.info("Принят запрос на создание пользователя, request = [{}]", input);
        var result = userEndpointBlockingStub.create(this.userMapper.create(input));
        log.info("Пользователь создан, response = [{}]", result);

        return this.userMapper.toCreateOutput(result);
    }

    /**
     * Обновление пользователя
     * @param input Входные данные для обновления {@link UserUpdateInput}
     * @return {@link UserUpdateOutput}
     */
    @DgsMutation
    public UserUpdateOutput updateUser(UserUpdateInput input) {
        log.info("Принят запрос на обновление пользователя, request = [{}]", input);
        var result = userEndpointBlockingStub.update(this.userMapper.update(input));
        log.info("Пользователь обновлен, response = [{}]", result);

        return this.userMapper.toUpdateOutput(result);
    }

    /**
     * Удаление пользователя
     * @param input Входные данные для удаления {@link UserRemoveInput}
     * @return {@link UserRemoveOutput}
     */
    @DgsMutation
    public UserRemoveOutput deleteUser(UserRemoveInput input) {
        log.info("Принят запрос на удаление пользователя, request = [{}]", input);
        var result = userEndpointBlockingStub.remove(this.userMapper.remove(input));
        log.info("Пользователь удален, response = [{}]", result);

        return this.userMapper.toRemoveOutput(result);
    }

    /**
     * Поиск пользователя по его ID
     * @param id ID пользователя
     * @return {@link User}
     */
    @DgsQuery
    public User getUserById(String id) {
        log.info("Принят запрос на поиск пользователя, id = [{}]", id);
        var result = userEndpointBlockingStub.findById(this.userMapper.toFindByIdRequest(id));
        log.info("Пользователь найден, response = [{}]", result);

        return this.userMapper.responseToUserOutput(result);
    }

    /**
     * Постраничный вывод множества пользователей
     * @param input Запрос страницы {@link PageRequest}
     * @return {@link Page}, {@link User}
     */
    @DgsQuery
    public Page<User> getUsersPage(PageRequest input) {
        log.info("Принят запрос на получение списка пользователей, input = [{}]", input);
        var result = userEndpointBlockingStub.findAll(this.paginationMapper.toPageRequest(input));
        log.info("Список пользователей получен, response.size = [{}]", result.getPage().getSize());

        return this.userMapper.toUserPage(result);
    }
}
