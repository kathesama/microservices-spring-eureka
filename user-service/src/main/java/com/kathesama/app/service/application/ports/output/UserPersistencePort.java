package com.kathesama.app.service.application.ports.output;

import com.kathesama.app.common.model.User;
import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
    User save(User product);

    boolean existsByUsername(String username);

    void deleteById(Long id);
}
