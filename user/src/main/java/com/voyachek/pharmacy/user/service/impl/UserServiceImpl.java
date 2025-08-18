package com.voyachek.pharmacy.user.service.impl;

import com.voyachek.pharmacy.grpclib.user.*;
import com.voyachek.pharmacy.grpclib.utils.page.PageUtil;
import com.voyachek.pharmacy.user.entity.User;
import com.voyachek.pharmacy.user.mapper.UserMapper;
import com.voyachek.pharmacy.user.repository.UserRepository;
import com.voyachek.pharmacy.user.service.UserService;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Сервис для работы с пользователем ({@link User})
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserCreateContract.UserCreateResponse createUser(UserCreateContract.UserCreateRequest request) {
        this.checkUserExistsOnCreate(request);
        var newUser = this.userMapper.createRequestToEntity(request);
        var createdUser = this.userRepository.save(newUser);
        return this.userMapper.entityToCreateResponse(createdUser);

    }

    @Override
    public UserUpdateContract.UserUpdateResponse updateUser(UserUpdateContract.UserUpdateRequest request) {
        var userUpdateCandidate = this.extractUserOrNotFound(request);
        this.checkUserConflictsOnUpdate(userUpdateCandidate, request);

        this.userMapper.updateEntity(request, userUpdateCandidate);
        var updatedUser = this.userRepository.save(userUpdateCandidate);

        return this.userMapper.entityToUpdateResponse(updatedUser, request);
    }

    @Override
    public UserRemoveContract.UserRemoveResponse removeUser(UserRemoveContract.UserRemoveRequest request) {
        var user = this.userRepository
                .findUserById(UUID.fromString(request.getId()))
                .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND));

        this.userRepository.delete(user);

        return this.userMapper.entityToRemoveResponse(user);
    }

    @Override
    public UserFindByIdContract.UserFindByIdResponse findById(UserFindByIdContract.UserFindByIdRequest request) {
        var user = this.userRepository
                .findUserById(UUID.fromString(request.getId()))
                .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND));

        return this.userMapper.entityToFindByIdResponse(user);
    }

    @Override
    public UserFindAllContract.UserFindAllResponse findAll(PageUtil.PageRequest request) {
        var page = this.userRepository.findAll(PageRequest.of(request.getPage(), request.getSize()))
                .map(this.userMapper::entityToFindByIdResponse);

        return this.userMapper.pageToFindAllResponse(page);
    }


    private void checkUserExistsOnCreate(UserCreateContract.UserCreateRequest request) {
        if (userRepository.existsByName(request.getName()) || userRepository.existsByEmail(request.getEmail())) {
            throw new StatusRuntimeException(Status.ALREADY_EXISTS, new Metadata());
        }
    }

    private User extractUserOrNotFound(UserUpdateContract.UserUpdateRequest request) {
        return this.userRepository
                .findUserById(UUID.fromString(request.getId()))
                .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND, new Metadata()));
    }

    private void checkUserConflictsOnUpdate(User user, UserUpdateContract.UserUpdateRequest request) {
        if (!request.getName().isEmpty()) {
            this.userRepository.findUserByNameAndIdNot(request.getName(), user.getId()).ifPresent(u -> {
                throw new StatusRuntimeException(Status.ALREADY_EXISTS, new Metadata());
            });
        }

        if (!request.getEmail().isEmpty()) {
            this.userRepository.findUserByEmailAndIdNot(request.getEmail(), user.getId()).ifPresent(u -> {
                throw new StatusRuntimeException(Status.ALREADY_EXISTS, new Metadata());
            });
        }
    }
}
