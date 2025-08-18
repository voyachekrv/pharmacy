package com.voyachek.pharmacy.user.mapper;

import com.voyachek.pharmacy.grpclib.user.*;
import com.voyachek.pharmacy.grpclib.utils.TimeUtil;
import com.voyachek.pharmacy.grpclib.utils.page.PageUtil;
import com.voyachek.pharmacy.user.entity.User;
import com.voyachek.pharmacy.user.entity.UserRole;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

/**
 * Маппер для сущности "Пользователь" {@link User}
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "role", source = "role", qualifiedByName = "roleFromProtobuf")
    @Mapping(target = "registrationDate", ignore = true)
    User createRequestToEntity(UserCreateContract.UserCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    void updateEntity(UserUpdateContract.UserUpdateRequest request, @MappingTarget User user);

    @AfterMapping
    default void ignoreEmptyStrings(UserUpdateContract.UserUpdateRequest request, @MappingTarget User user) {
        if (!request.getName().isEmpty()) {
            user.setName(request.getName());
        }
        if (!request.getEmail().isEmpty()) {
            user.setEmail(request.getEmail());
        }
    }

    default UserCreateContract.UserCreateResponse entityToCreateResponse(User user) {
        return UserCreateContract.UserCreateResponse.newBuilder()
                .setId(user.getId().toString())
                .build();
    }

    default UserUpdateContract.UserUpdateResponse entityToUpdateResponse(User user, UserUpdateContract.UserUpdateRequest request) {
        var builder = UserUpdateContract.UserUpdateResponse.newBuilder()
                .setId(user.getId().toString());

        if (!request.getName().isEmpty()) {
            builder.setName(user.getName());
        }

        if (!request.getEmail().isEmpty()) {
            builder.setEmail(user.getEmail());
        }

        return builder.build();
    }

    default UserRemoveContract.UserRemoveResponse entityToRemoveResponse(User user) {
        return UserRemoveContract.UserRemoveResponse.newBuilder()
                .setId(user.getId().toString())
                .setName(user.getName())
                .build();
    }

    default UserFindByIdContract.UserFindByIdResponse entityToFindByIdResponse(User user) {
        return UserFindByIdContract.UserFindByIdResponse
                .newBuilder()
                .setId(user.getId().toString())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setRole(UserRoleEnum.UserRole.valueOf(user.getRole().name()))
                .setRegistrationDate(TimeUtil.toTimestamp(user.getRegistrationDate()))
                .build();
    }

    default UserFindAllContract.UserFindAllResponse pageToFindAllResponse(Page<UserFindByIdContract.UserFindByIdResponse> page) {
        var pageResponse = PageUtil.PageResponse.newBuilder()
                .setPage(page.getNumber())
                .setSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages())
                .setLast(page.isLast())
                .build();

        return UserFindAllContract.UserFindAllResponse.newBuilder()
                .addAllContent(page.getContent())
                .setPage(pageResponse)
                .build();
    }

    @Named("roleFromProtobuf")
    default UserRole roleFromProtobuf(UserRoleEnum.UserRole role) {
        return UserRole.fromProtobuf(role.getNumber());
    }
}
