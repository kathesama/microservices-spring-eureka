package com.kathesama.app.service.infrastructure.adapter.output.persistence.mapper;

import com.kathesama.app.service.domain.model.Item;
import com.kathesama.app.service.infrastructure.adapter.output.persistence.entity.ItemEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemPersistenceMapper {
    ItemEntity toItemEntity(Item item);
    Item toItem(ItemEntity entity);
    List<Item> toItemList(List<ItemEntity> itemList);
}
