package com.kathesama.app.service.application.service;

import com.kathesama.app.service.application.ports.input.ItemServiceInputPort;
import com.kathesama.app.service.application.ports.output.ProductClientRest;
import com.kathesama.app.service.domain.model.Item;
import com.kathesama.app.service.infrastructure.adapter.input.rest.mapper.ProductRestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("itemServiceFeign")
@Primary
@RequiredArgsConstructor
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
        log.info("looking register for product: {}", id);
        return new Item(productMapper.toProduct(feignPersistencePort.findById(id)), quantity);
    }
}
