package az.azericard.serviceproduct.service.impl;

import az.azericard.core.domain.OperationResponse;
import az.azericard.serviceproduct.domain.dto.ProductDto;
import az.azericard.serviceproduct.domain.entity.Product;
import az.azericard.serviceproduct.repository.ProductRepository;
import az.azericard.serviceproduct.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductByName(String productName) {
        return productRepository.findByName(productName)
                .map((product) -> {
                    var productDto = new ProductDto();
                    productDto.setName(product.getName());
                    productDto.setCategory(product.getCategory());
                    productDto.setPrice(product.getPrice());
                    productDto.setQuantityInStock(product.getQuantityInStock());
                    return productDto;
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public OperationResponse decrementStock(String productName) {
        Objects.requireNonNull(productName, "productName can't be null");

        Optional<Product> optionalProduct = productRepository.findByName(productName);
        if (optionalProduct.isEmpty()) {
            return new OperationResponse(false, "Product doesn't exist!");
        }

        Product product = optionalProduct.get();
        product.setQuantityInStock(product.getQuantityInStock() - 1);
        productRepository.save(product);

        return new OperationResponse(true, "Stock count is decremented for " + productName);
    }

}
