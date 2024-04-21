package com.kathesama.app.service.infrastructure.adapter.output.persistence;

import com.kathesama.app.service.application.ports.output.UserPersistencePort;
import com.kathesama.app.service.infrastructure.adapter.output.persistence.mapper.UserPersistenceMapper;
import com.kathesama.app.service.infrastructure.adapter.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.kathesama.app.common.model.User;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {
    private final UserRepository repository;
    private final UserPersistenceMapper mapper;

    @Override
    public List<User> findAll() {
        return mapper.toUsers(repository.findAll());
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(mapper::toUser);
    }

    @Override
    public User save(User user) {
        return mapper.toUser(
            repository.save(
                mapper.toUserEntity(user)
            )
        );
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
