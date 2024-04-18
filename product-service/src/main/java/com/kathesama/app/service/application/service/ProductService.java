package com.kathesama.app.service.application.service;

import com.kathesama.app.service.application.ports.input.ProductServiceInputPort;
import com.kathesama.app.service.application.ports.output.ProductPersistencePort;
import com.kathesama.app.service.domain.exception.ProductNotFoundException;
import com.kathesama.app.common.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInputPort {
    private final ProductPersistencePort persistencePort;
    @Override
    public List<Product> findAll() {
        return persistencePort.findAll();
    }

    /*
    * Added SneakyThrows for launching InterruptedException when needed
    */
    @SneakyThrows
    @Override
    public Product findById(Long id)  {
        log.info("looking record for product: {}", id);

        // [CODE FOR TESTING RESILIENCE4J]
        // product not found
        if(id.equals(10L)) {
            log.error("Product not found: {}", id);
            throw new ProductNotFoundException();
        }

        // slow call
        if(id.equals(7L)) {
            log.error("Timeout error trying to found Product: {}", id);
            TimeUnit.SECONDS.sleep(5L);
        }
        // [END OF CODE FOR TESTING RESILIENCE4J]

        return persistencePort.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public Product save(Product product) {
        log.info("Creating register for product: {}", product.getName());
        return persistencePort.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        log.info("Updating register for product: {}", product.getId());
        return persistencePort.findById(id)
                .map((Product savedProduct) -> {
                    savedProduct.setName(product.getName());
                    savedProduct.setDescription(product.getDescription());
                    savedProduct.setPrice(product.getPrice());

                    return persistencePort.save(savedProduct);
                })
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting register for product: {}", id);
        if (persistencePort.findById(id).isEmpty()) {
            throw new ProductNotFoundException();
        }

        persistencePort.deleteById(id);
    }

    @Override
    public boolean existsBySku(String sku) {
        return false;
    }
}
