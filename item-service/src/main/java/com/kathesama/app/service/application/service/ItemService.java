package com.kathesama.app.service.application.service;

import com.kathesama.app.common.model.Product;
import com.kathesama.app.service.application.ports.input.ItemServiceInputPort;
import com.kathesama.app.service.application.ports.output.ItemPersistencePort;
import com.kathesama.app.service.domain.exception.ItemNotFoundException;
import com.kathesama.app.service.domain.model.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService implements ItemServiceInputPort {
    private final ItemPersistencePort persistencePort;
    private final RestTemplate restClient;
    @Override
    public List<Item> findALl() {
        List<Product> products = Arrays.asList(restClient.getForObject("http://localhost:8000/products/api/v1", Product[].class));
        return products.stream().map(product -> new Item(product, 1L)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Long quantity) {
        log.info("looking register for product: {}", id);
        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id", id.toString());

        Product product = restClient.getForObject("http://localhost:8000/products/api/v1/{id}", Product.class, pathVariables);
        return new Item(product, quantity);
    }
}
