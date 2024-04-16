package com.kathesama.app.service.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.service.application.ports.input.ItemServiceInputPort;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.ItemResponse;
import com.kathesama.app.service.infrastructure.adapter.input.rest.mapper.ItemRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    @Qualifier("itemServiceFeign")
    private final ItemServiceInputPort itemService;

    private final ItemRestMapper itemMapper;

    @GetMapping("/api/v1")
    public List<ItemResponse> findAll() {
        return itemMapper.toItemResponseList(itemService.findALl());
    }

    @GetMapping("/api/v1/{productId}/{quantity}")
    public ItemResponse findByProductId(@PathVariable Long productId, @PathVariable Long quantity) {
        return itemMapper.toItemResponse(itemService.findById(productId, quantity));
    }
}
