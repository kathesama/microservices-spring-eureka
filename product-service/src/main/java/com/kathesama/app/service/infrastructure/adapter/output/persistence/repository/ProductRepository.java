package com.kathesama.app.service.infrastructure.adapter.output.persistence.repository;

import com.kathesama.app.service.infrastructure.adapter.output.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}

