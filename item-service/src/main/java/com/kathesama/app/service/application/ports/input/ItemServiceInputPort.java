package com.kathesama.app.service.application.ports.input;

import com.kathesama.app.common.model.Product;
import com.kathesama.app.service.domain.model.Item;

import java.util.List;

public interface ItemServiceInputPort {
    List<Item> findALl();
    Item findById(Long id, Long quantity);
    Product save(Product product);
    Product update(Long id, Product product);
    void delete(Long id);
}
