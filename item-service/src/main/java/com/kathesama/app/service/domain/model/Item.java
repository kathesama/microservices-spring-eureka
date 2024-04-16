package com.kathesama.app.service.domain.model;

import com.kathesama.app.common.model.Product;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Product product;
    private Long quantity;

    public Double getTotal() {
        return quantity * product.getPrice();
    }
}