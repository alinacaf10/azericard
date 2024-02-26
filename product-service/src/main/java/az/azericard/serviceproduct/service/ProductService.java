package az.azericard.serviceproduct.service;

import az.azericard.core.domain.OperationResponse;
import az.azericard.serviceproduct.domain.dto.ProductDto;
import az.azericard.serviceproduct.domain.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProduct();

    ProductDto getProductByName(String product);

    OperationResponse decrementStock(String product);
}
