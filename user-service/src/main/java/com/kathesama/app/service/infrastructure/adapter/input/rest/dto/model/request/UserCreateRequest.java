package com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.request;

import com.kathesama.app.service.domain.validation.ExistsByUsername;
import com.kathesama.app.common.util.ConfigurationCatalog;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "{NotBlank.product.name}")
    @Size(min = 3, max = 45)
    @ExistsByUsername(message="{ExistsUsername.user.username}")
    String username;

    @Nullable
    private Boolean enabled = false; // Valor por defecto: false

    @Nullable
    @Pattern(
            regexp = ConfigurationCatalog.PASSWORD_REGEX,
            message = "{WhenIsPresent.user.password.message}"
    )
    private String password;
}
