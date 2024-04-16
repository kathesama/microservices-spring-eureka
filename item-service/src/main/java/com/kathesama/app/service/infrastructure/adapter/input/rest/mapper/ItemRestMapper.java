package com.kathesama.app.service.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.service.domain.model.Item;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.request.ProductCreateRequest;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.ItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE //ignorar los campos que no se mapean
)
public interface ItemRestMapper {
    Item toItem(ProductCreateRequest request);
    ItemResponse toItemResponse(Item item);
    List<ItemResponse> toItemResponseList(List<Item> itemList);
}