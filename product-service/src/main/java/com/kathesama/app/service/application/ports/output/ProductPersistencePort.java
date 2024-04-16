package com.kathesama.app.service.application.ports.output;

import com.kathesama.app.common.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductPersistencePort {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
    boolean existsBySku(String sku);
}
