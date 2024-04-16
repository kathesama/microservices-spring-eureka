package com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response;

import com.kathesama.app.common.model.Product;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private Product product;
    private Long quantity;
}
