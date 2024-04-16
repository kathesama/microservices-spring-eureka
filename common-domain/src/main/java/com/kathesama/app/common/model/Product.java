package com.kathesama.app.common.model;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private Date createAt;
    private Date updatedAt;

    String name;
    String description;
    Double price;
}
