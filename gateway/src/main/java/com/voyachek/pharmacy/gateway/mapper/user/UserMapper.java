package com.voyachek.pharmacy.gateway.mapper.user;

import com.google.protobuf.Timestamp;
import com.voyachek.pharmacy.gateway.data.user.*;
import com.voyachek.pharmacy.gateway.data.utils.Page;
import com.voyachek.pharmacy.gateway.data.utils.PageInfo;
import com.voyachek.pharmacy.grpclib.user.*;
import com.voyachek.pharmacy.grpclib.utils.TimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Optional;

/**
 * Маппер для сущности "Пользователь"
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    UserCreateOutput toCreateOutput(UserCreateContract.UserCreateResponse input);

    default UserCreateContract.UserCreateRequest create(UserCreateInput input) {
        return UserCreateContract.UserCreateRequest.newBuilder()
                .setName(input.getName())
                .setEmail(input.getEmail())
                .setRole(input.getRole().toProtobuf())
                .build();
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    UserUpdateOutput toUpdateOutput(UserUpdateContract.UserUpdateResponse input);

    default UserUpdateContract.UserUpdateRequest update(UserUpdateInput input) {
        return UserUpdateContract.UserUpdateRequest.newBuilder()
                .setName(Optional.ofNullable(input.getName()).orElse(""))
                .setEmail(Optional.ofNullable(input.getEmail()).orElse(""))
                .setId(input.getId())
                .build();
    }

    default UserRemoveContract.UserRemoveRequest remove(UserRemoveInput input) {
        return UserRemoveContract.UserRemoveRequest.newBuilder()
                .setId(input.getId())
                .build();
    }

    UserRemoveOutput toRemoveOutput(UserRemoveContract.UserRemoveResponse input);

    @Mapping(target = "role", source = "role", qualifiedByName = "roleFromProtobuf")
    @Mapping(target = "registrationDate", source = "registrationDate", qualifiedByName = "stringifyTimeStamp")
    User responseToUserOutput(UserFindByIdContract.UserFindByIdResponse input);

    default UserFindByIdContract.UserFindByIdRequest toFindByIdRequest(String id) {
        return UserFindByIdContract.UserFindByIdRequest
                .newBuilder()
                .setId(id)
                .build();
    }

    default Page<User> toUserPage(UserFindAllContract.UserFindAllResponse response) {
        var content = response
                .getContentList()
                .stream()
                .map(c -> User
                        .builder()
                        .id(c.getId())
                        .name(c.getName())
                        .email(c.getEmail())
                        .role(UserRole.valueOf(c.getRole().name()))
                        .registrationDate(TimeUtil.fromTimestamp(c.getRegistrationDate()).toString())
                        .build())
                .toList();

        var pageInfo = PageInfo.builder()
                .page(response.getPage().getPage())
                .last(response.getPage().getLast())
                .size(response.getPage().getSize())
                .totalElements(response.getPage().getTotalElements())
                .totalPages(response.getPage().getTotalPages())
                .build();

        return Page.<User>builder()
                .content(content)
                .pageInfo(pageInfo)
                .build();
    }

    @Named("roleFromProtobuf")
    default UserRole roleFromProtobuf(UserRoleEnum.UserRole role) {
        return UserRole.valueOf(role.name());
    }

    @Named("stringifyTimeStamp")
    default String stringifyTimeStamp(Timestamp ts) {
        return TimeUtil.fromTimestamp(ts).toString();
    }
}
