package com.kathesama.app.service.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.common.model.Product;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.request.ProductCreateRequest;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE //ignorar los campos que no se mapean
)
public interface ProductRestMapper {
    Product toProduct(ProductCreateRequest request);
    Product toProduct(ProductResponse productResponse);

    ProductResponse toProductResponse(Product product);
    ProductCreateRequest toProductCreateRequest(Product product);

    List<ProductResponse> toProductResponseList(List<Product> productList);
}
