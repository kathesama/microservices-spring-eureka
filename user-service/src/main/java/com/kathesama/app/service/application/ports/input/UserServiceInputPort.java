package com.kathesama.app.service.application.ports.input;

import com.kathesama.app.common.model.User;

import java.util.List;

public interface UserServiceInputPort {
    List<User> findAll();

    User findById(Long id);

    User save(User user);
    User update(Long id, User user);

    boolean existsByUsername(String username);

    User findByUsername(String username);

    void deleteById(Long id);
}
