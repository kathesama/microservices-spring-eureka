package com.kathesama.app.service.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.common.model.Product;
import com.kathesama.app.service.application.ports.input.ItemServiceInputPort;
import com.kathesama.app.service.domain.model.Item;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.request.ProductCreateRequest;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.ItemResponse;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.ProductResponse;
import com.kathesama.app.service.infrastructure.adapter.input.rest.mapper.ItemRestMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemServiceInputPort itemService;

    private final ItemRestMapper itemMapper;

    private final CircuitBreakerFactory cbFactory;

    public ItemController(@Qualifier("itemServiceFeign") ItemServiceInputPort itemService,
                          ItemRestMapper itemMapper,
                          CircuitBreakerFactory cbFactory) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
        this.cbFactory = cbFactory;
    }

    @GetMapping("/api/v1")
    public List<ItemResponse> findAll() {
        return itemMapper.toItemResponseList(itemService.findALl());
    }

    @GetMapping("/api/v1/{productId}/{quantity}")
    public ItemResponse findByProductId(@PathVariable Long productId, @PathVariable Long quantity) {
        return itemMapper.toItemResponse(itemService.findById(productId, quantity));
    }

    // [CODE FOR TESTING RESILIENCE4J]
    @GetMapping("/api/v2/{productId}/{quantity}")
    public ItemResponse findByProductIdFactory(@PathVariable Long productId, @PathVariable Long quantity) {
        return cbFactory
            .create("items")
            .run(() -> itemMapper.toItemResponse(itemService.findById(productId, quantity)),
                 (error) ->   alternativeCircuit(productId, quantity, error));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "alternativeCircuit") //usando anotaciones la configuración solo funciona via .yml
    @GetMapping("/api/v3/{productId}/{quantity}")
    public ItemResponse findByProductIdAnnotations(@PathVariable Long productId, @PathVariable Long quantity) {
        return itemMapper.toItemResponse(itemService.findById(productId, quantity));
    }

    @TimeLimiter(name = "items", fallbackMethod = "alternativeCircuitTimer") //usando anotaciones la configuración solo funciona via .yml
    @GetMapping("/api/v4/{productId}/{quantity}")
    private CompletableFuture<ItemResponse> findByProductIdAnnotationsV2(@PathVariable Long productId, @PathVariable Long quantity) {
        return CompletableFuture.supplyAsync(() -> itemMapper.toItemResponse(itemService.findById(productId, quantity)));
    }

    private ItemResponse alternativeCircuit(Long id, Long quantity, Throwable error) {
        log.warn("---- Thrown alternate way to findByProductId: {}, got by: " + error.getMessage(), id);

        ItemResponse item = new ItemResponse();
        Product producto = new Product();

        producto.setId(id);
        item.setQuantity(quantity);
        producto.setName("Camara Sony");
        producto.setPrice(500.00);
        item.setProduct(producto);

        System.out.println(item);

        return item;
    }

    private CompletableFuture<ItemResponse> alternativeCircuitTimer(Long id, Long quantity, Throwable error) {
        log.warn("Thrown alternate way to findByProductId: {}, got by timeout, details: " + error.getMessage(), id);

        Item item = new Item();
        Product producto = new Product();

        producto.setId(id);
        item.setQuantity(quantity);
        producto.setName("Camara Sony");
        producto.setPrice(500.00);
        item.setProduct(producto);

        System.out.println(item);

        return CompletableFuture.supplyAsync(() -> itemMapper.toItemResponse(item));
    }

    private ItemResponse getAlternativeCircuitValue(Long id, Long quantity){
        Item item = new Item();
        Product producto = new Product();

        producto.setId(id);
        item.setQuantity(quantity);
        producto.setName("Camara Sony");
        producto.setPrice(500.00);
        item.setProduct(producto);

        System.out.println(item);

        return itemMapper.toItemResponse(item);
    }
    // [END OF CODE FOR TESTING RESILIENCE4J]

    @PostMapping("/api/v1")
    public Product createProduct(@RequestBody Product product) {
        return itemService.save(product);
    }

    @PutMapping("/api/v1/{id}")
    public Product editProduct(@PathVariable Long id, @RequestBody Product product) {
        return itemService.update(id, product);
    }

    @DeleteMapping("/api/v1/{id}")
    public void deleteProduct(@PathVariable Long id) {
        itemService.delete(id);
    }
}
