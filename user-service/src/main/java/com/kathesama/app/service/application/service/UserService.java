package com.kathesama.app.service.application.service;

import com.kathesama.app.service.application.ports.input.UserServiceInputPort;
import com.kathesama.app.service.application.ports.output.UserPersistencePort;
import com.kathesama.app.common.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kathesama.app.common.exception.UserNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInputPort {
    private final UserPersistencePort persistencePort;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return persistencePort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        log.info("looking record for user: {}", id);

        return persistencePort.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public User save(User user) {
        log.info("Creating register for product: {}", user.getUsername());
        return persistencePort.save(user);
    }

    @Override
    public User update(Long id, User user) {
        log.info("Updating register for product: {}", id);
        return persistencePort.findById(id)
                .map((User savedUser) -> {
                    savedUser.setUsername(user.getUsername());
                    savedUser.setEnabled(user.getEnabled());

                    return persistencePort.save(savedUser);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public boolean existsByUsername(String username) {
        return persistencePort.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        log.info("userService - finding register for user: {}", username);
        return persistencePort
                .findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("Username %s doesn't exists!", username)));
    }

    @Override
    public void deleteById(Long id) {
        log.info("userService - deleting register for user: {}", id);
        if(persistencePort.findById(id).isEmpty()){
            throw new UserNotFoundException();
        }

        persistencePort.deleteById(id);
    }
}
