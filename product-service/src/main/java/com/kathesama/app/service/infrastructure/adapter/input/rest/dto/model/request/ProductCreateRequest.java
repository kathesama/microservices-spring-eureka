package com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {
    //NotEmpty pasa un string con caracter blanco, para este caso se usa notBlank para que no pase
    @NotBlank(message = "Field name cannot be empty or null.")
    private String name;

    @NotBlank(message = "Field description cannot be empty or null.")
    private String description;

    @NotNull(message = "Field price cannot be null.")
    private Double price;
}
