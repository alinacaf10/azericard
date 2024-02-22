package az.azericard.serviceproduct.controller;

import az.azericard.serviceproduct.domain.entity.Product;
import az.azericard.serviceproduct.domain.enumeration.ProductCategory;
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

    @GetMapping()
    public ResponseEntity<List<Product>> getAllUsers() {
        List<Product> products = productService.getAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<Product> getProductByName(@PathVariable String productName) {
        Product product = productService.getProductByName(productName);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable ProductCategory category) {
        List<Product> products = productService.getProductsByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable int id,
            @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product with id:" + id + " successfully deleted", HttpStatus.OK);
    }

}
