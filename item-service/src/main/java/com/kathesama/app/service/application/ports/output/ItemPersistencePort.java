package com.kathesama.app.service.application.ports.output;

import com.kathesama.app.service.domain.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemPersistencePort {
    public List<Item> findALl();
    public Optional<Item> findById(Long id);
}
