package com.voyachek.pharmacy.user.grpc;

import com.voyachek.pharmacy.grpclib.user.*;
import com.voyachek.pharmacy.grpclib.utils.page.PageUtil;
import com.voyachek.pharmacy.user.entity.User;
import com.voyachek.pharmacy.user.service.UserService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

/**
 * GRPC-обработчик для работы с пользователями ({@link User})
 */
@GrpcService
@RequiredArgsConstructor
@Slf4j
public class UserGrpcHandler extends UserEndpointGrpc.UserEndpointImplBase {

    private final UserService userService;

    /**
     * Добавление нового пользователя
     * @param request Контракт на создание пользователя ({@link UserCreateContract.UserCreateRequest})
     * @param responseObserver {@link StreamObserver<UserCreateContract.UserCreateResponse>}
     */
    @Override
    public void create(UserCreateContract.UserCreateRequest request,
                        StreamObserver<UserCreateContract.UserCreateResponse> responseObserver) {
        log.info("Принят запрос на создание пользователя, request = [{}]", request);
        var result = userService.createUser(request);
        log.info("Пользователь создан, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    /**
     * Обновление пользователя
     * @param request Контракт на обновление пользователя ({@link UserUpdateContract.UserUpdateRequest})
     * @param responseObserver {@link StreamObserver<UserUpdateContract.UserUpdateResponse>}
     */
    @Override
    public void update(UserUpdateContract.UserUpdateRequest request,
                       StreamObserver<UserUpdateContract.UserUpdateResponse> responseObserver) {
        log.info("Принят запрос на обновление пользователя, request = [{}]", request);
        var result = userService.updateUser(request);
        log.info("Пользователь обновлен, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    /**
     * Удаление пользователя
     * @param request Контракт на удаление пользователя ({@link UserRemoveContract.UserRemoveRequest})
     * @param responseObserver {@link StreamObserver<UserRemoveContract.UserRemoveResponse>}
     */
    @Override
    public void remove(UserRemoveContract.UserRemoveRequest request,
                       StreamObserver<UserRemoveContract.UserRemoveResponse> responseObserver) {
        log.info("Принят запрос на удаление пользователя, request = [{}]", request);
        var result = userService.removeUser(request);
        log.info("Пользователь удален, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    /**
     * Поиск пользователя по ID
     * @param request Контракт на поиск пользователя по ID ({@link UserFindByIdContract.UserFindByIdRequest})
     * @param responseObserver {@link StreamObserver<UserFindByIdContract.UserFindByIdResponse>}
     */
    @Override
    public void findById(UserFindByIdContract.UserFindByIdRequest request,
                         StreamObserver<UserFindByIdContract.UserFindByIdResponse> responseObserver) {
        log.info("Принят запрос на поиск пользователя, request = [{}]", request);
        var result = userService.findById(request);
        log.info("Пользователь найден, response = [{}]", result);

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    /**
     * Получение страницы списка пользователей
     * @param request Запрос на постраничный поиск ({@link PageUtil.PageRequest})
     * @param responseObserver {@link StreamObserver<UserFindAllContract.UserFindAllResponse>}
     */
    @Override
    public void findAll(PageUtil.PageRequest request,
                        StreamObserver<UserFindAllContract.UserFindAllResponse> responseObserver) {
        log.info("Принят запрос на получение списка пользователей, request = [{}]", request);
        var result = userService.findAll(request);
        log.info("Пользователи найдены, length = [{}]", result.getContentCount());

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
