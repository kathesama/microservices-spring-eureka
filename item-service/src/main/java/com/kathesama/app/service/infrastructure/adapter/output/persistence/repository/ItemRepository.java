package com.kathesama.app.service.infrastructure.adapter.output.persistence.repository;

import com.kathesama.app.service.infrastructure.adapter.output.persistence.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
