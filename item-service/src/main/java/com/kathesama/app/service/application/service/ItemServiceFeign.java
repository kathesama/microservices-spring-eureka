package com.kathesama.app.service.application.service;

import com.kathesama.app.common.model.Product;
import com.kathesama.app.service.application.ports.input.ItemServiceInputPort;
import com.kathesama.app.service.application.ports.output.ProductClientRest;
import com.kathesama.app.service.domain.model.Item;
import com.kathesama.app.service.infrastructure.adapter.input.rest.mapper.ProductRestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
//@Primary
@RequiredArgsConstructor
@Service("itemServiceFeign")
public class ItemServiceFeign implements ItemServiceInputPort {
    private final ProductClientRest feignPersistencePort;
    private final ProductRestMapper productMapper;

    @Override
    public List<Item> findALl() {
        return feignPersistencePort
                .findAll()
                    .stream()
                    .map(product -> new Item(productMapper.toProduct(product), 1L))
                    .collect(Collectors.toList()
                );
    }

    @Override
    public Item findById(Long id, Long quantity) {
        log.info("ItemServiceFeign - looking register for product: {}", id);
        return new Item(productMapper.toProduct(feignPersistencePort.findById(id)), quantity);
    }

    @Override
    public Product save(Product product) {
        log.info("ItemServiceFeign - Creating register for product: {}", product.getName());
        return productMapper.toProduct(
                feignPersistencePort.create(
                    productMapper.toProductCreateRequest(product)
                )
        );
    }

    @Override
    public Product update(Long id, Product product) {
        log.info("ItemServiceFeign - Creating register for product: {}", id);
        return productMapper.toProduct(
                feignPersistencePort.update(
                        id,
                        productMapper.toProductCreateRequest(product)
                )
        );
    }

    @Override
    public void delete(Long id) {
        log.info("ItemServiceFeign - deleting register for product: {}", id);
        feignPersistencePort.delete(id);
    }
}
