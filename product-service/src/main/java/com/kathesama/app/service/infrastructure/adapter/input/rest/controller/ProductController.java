package com.kathesama.app.service.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.service.application.ports.input.ProductServiceInputPort;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.request.ProductCreateRequest;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.ProductResponse;
import com.kathesama.app.service.infrastructure.adapter.input.rest.mapper.ProductRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins="http://localhost:4200", originPatterns = "*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductServiceInputPort productService;

    private final ProductRestMapper productMapper;

    @GetMapping("/api/v1")
    public List<ProductResponse> findAll() {
        return productMapper.toProductResponseList(productService.findAll());
    }

    @GetMapping("/api/v1/{id}")
    public ProductResponse findById(@PathVariable Long id) {

        return productMapper.toProductResponse(productService.findById(id));
    }

    @PostMapping("/api/v1")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreateRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    productMapper.toProductResponse(
                        productService.save(
                                productMapper.toProduct(productRequest)
                        )
                    )
                );
    }

    @PutMapping("/api/v1/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductCreateRequest productRequest) {
        log.info("Updating product id: {}", id);
        return productMapper.toProductResponse(
                productService.update(id, productMapper.toProduct(productRequest)
            )
        );
    }

    @DeleteMapping("/api/v1/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
