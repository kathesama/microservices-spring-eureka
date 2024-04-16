package com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;

    String name;
    String description;
    Double price;
}
