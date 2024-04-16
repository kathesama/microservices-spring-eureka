package com.kathesama.app.service.infrastructure.adapter.output.persistence.mapper;

import com.kathesama.app.common.model.Product;
import com.kathesama.app.service.infrastructure.adapter.output.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {
    ProductEntity toProductEntity(Product product);
    Product toProduct(ProductEntity entity);
    List<Product> toProductList(List<ProductEntity> productList);
}
