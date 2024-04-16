package com.kathesama.app.service.application.ports.input;

import com.kathesama.app.common.model.Product;

import java.util.List;

public interface ProductServiceInputPort {
    List<Product> findAll();
    Product findById(Long id);

    Product save(Product product);

    Product update(Long id, Product product);

    void deleteById(Long id);

    boolean existsBySku(String sku);
}
