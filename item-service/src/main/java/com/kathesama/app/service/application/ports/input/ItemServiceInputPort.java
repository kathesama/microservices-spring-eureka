package com.kathesama.app.service.application.ports.input;

import com.kathesama.app.service.domain.model.Item;

import java.util.List;

public interface ItemServiceInputPort {
    List<Item> findALl();
    public Item findById(Long id, Long quantity);
}
