package com.kathesama.app.service.infrastructure.adapter.output.persistence.mapper;

import com.kathesama.app.common.model.User;
import com.kathesama.app.service.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {
    UserEntity toUserEntity(User user);
    User toUser(UserEntity userEntity);
    List<User> toUsers(List<UserEntity> users);
}
