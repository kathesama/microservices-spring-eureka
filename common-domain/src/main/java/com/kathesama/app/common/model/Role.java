package com.kathesama.app.common.model;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Long id;
    private Date createAt;
    private Date updatedAt;
    String name;
}
