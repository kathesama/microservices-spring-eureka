package com.kathesama.app.service.application.ports.output;

import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.request.ProductCreateRequest;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.ProductResponse;
import com.kathesama.app.service.infrastructure.interceptor.ItemProductClientConfiguration;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", configuration = ItemProductClientConfiguration.class)
public interface ProductClientRest {
    @GetMapping("/products/api/v1")
    public List<ProductResponse> findAll();

    @GetMapping("/products/api/v1/{id}")
    public ProductResponse findById(@PathVariable Long id);

    @PostMapping("/products/api/v1")
    public ProductResponse create(@Valid @RequestBody ProductCreateRequest productRequest);

    @PutMapping("/api/v1/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductCreateRequest productRequest);

    @DeleteMapping("/api/v1/{id}")
    public void delete(@PathVariable Long id);
}
