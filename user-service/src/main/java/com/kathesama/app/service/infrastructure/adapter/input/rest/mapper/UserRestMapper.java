package com.kathesama.app.service.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.common.model.User;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.request.UserCreateRequest;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE //ignorar los campos que no se mapean
)
public interface UserRestMapper {
    User toUser(UserCreateRequest request);
    UserResponse toUserResponse(User user);
    List<UserResponse> toUserResponseList(List<User> userList);
}
