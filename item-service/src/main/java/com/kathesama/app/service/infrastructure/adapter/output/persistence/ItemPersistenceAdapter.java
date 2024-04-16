package com.kathesama.app.service.infrastructure.adapter.output.persistence;

import com.kathesama.app.service.application.ports.output.ItemPersistencePort;
import com.kathesama.app.service.domain.model.Item;
import com.kathesama.app.service.infrastructure.adapter.output.persistence.mapper.ItemPersistenceMapper;
import com.kathesama.app.service.infrastructure.adapter.output.persistence.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemPersistenceAdapter implements ItemPersistencePort {
    private final ItemRepository repository;
    private final ItemPersistenceMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Item> findALl() {
        return mapper.toItemList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Item> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toItem);
    }
}
