package com.kathesama.app.service.infrastructure.adapter.output.persistence;

import com.kathesama.app.service.application.ports.output.ProductPersistencePort;
import com.kathesama.app.common.model.Product;
import com.kathesama.app.service.infrastructure.adapter.output.persistence.mapper.ProductPersistenceMapper;
import com.kathesama.app.service.infrastructure.adapter.output.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistencePort {
    private final ProductRepository repository;
    private final ProductPersistenceMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return mapper.toProductList(repository.findAll());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toProduct);
    }

    @Override
    public Product save(Product product) {
        return mapper.toProduct(
                repository.save(
                        mapper.toProductEntity(product)
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsBySku(String sku) {
        return false;
    }
}
