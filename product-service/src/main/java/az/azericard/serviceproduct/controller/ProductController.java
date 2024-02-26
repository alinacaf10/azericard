package az.azericard.serviceproduct.controller;

import az.azericard.core.domain.OperationResponse;
import az.azericard.serviceproduct.domain.dto.ProductDto;
import az.azericard.serviceproduct.domain.entity.Product;
import az.azericard.serviceproduct.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ProductDto> getProduct(@RequestParam("product") String product) {
        ProductDto productDto = productService.getProductByName(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PutMapping("/{product}/decrement-stock")
    public ResponseEntity<OperationResponse> decrementStockFor(@PathVariable("product") String product) {
        OperationResponse operationResponse = productService.decrementStock(product);
        return new ResponseEntity<>(operationResponse, HttpStatus.OK);
    }
}
