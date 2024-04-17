package com.kathesama.app.service.application.ports.output;

import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service", url = "localhost:8000")
public interface ProductClientRest {
    @GetMapping("/products/api/v1")
    public List<ProductResponse> findAll();

    @GetMapping("/products/api/v1/{id}")
    public ProductResponse findById(@PathVariable Long id);
}
