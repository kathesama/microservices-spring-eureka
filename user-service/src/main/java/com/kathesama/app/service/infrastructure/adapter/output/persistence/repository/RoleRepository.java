package com.kathesama.app.service.infrastructure.adapter.output.persistence.repository;

import com.kathesama.app.service.infrastructure.adapter.output.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
