package com.kathesama.app.common.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private Date createAt;
    private Date updatedAt;
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastname;
    private Boolean enabled;
    private List<Role> roles;
}
