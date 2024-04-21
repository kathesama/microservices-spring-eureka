package com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    String username;
    String name;
    String lastname;
    String email;
    Boolean enabled;
}
