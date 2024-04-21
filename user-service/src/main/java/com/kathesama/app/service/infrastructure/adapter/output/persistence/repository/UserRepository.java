package com.kathesama.app.service.infrastructure.adapter.output.persistence.repository;

import com.kathesama.app.service.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long>, PagingAndSortingRepository<UserEntity,Long> {
    boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
}
