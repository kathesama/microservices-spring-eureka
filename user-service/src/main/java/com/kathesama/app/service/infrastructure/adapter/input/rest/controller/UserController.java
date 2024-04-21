package com.kathesama.app.service.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.service.application.ports.input.UserServiceInputPort;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.request.UserCreateRequest;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.UserResponse;
import com.kathesama.app.service.infrastructure.adapter.input.rest.mapper.UserRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServiceInputPort userService;

    private final UserRestMapper userMapper;

    @GetMapping("/api/v1")
    public List<UserResponse> findAll() {
        return userMapper.toUserResponseList(userService.findAll());
    }

    @GetMapping("/api/v1/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userMapper.toUserResponse(userService.findById(id));
    }

    @PostMapping("/api/v1")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userMapper.toUserResponse(
                userService.save(
                        userMapper.toUser(userRequest)
                    )
                )
            );
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<UserResponse> publicRegisterForNewUser(@Valid @RequestBody UserCreateRequest userRequest) {
        return null;
    }

    @PutMapping("/v1/api/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserCreateRequest request) {
        return userMapper.toUserResponse(
                userService.update(id, userMapper.toUser(request)));
    }

    @DeleteMapping("/api/v1/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
