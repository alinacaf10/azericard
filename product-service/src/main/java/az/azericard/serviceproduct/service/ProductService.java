package az.azericard.serviceproduct.service;

import az.azericard.serviceproduct.domain.entity.Product;
import az.azericard.serviceproduct.domain.enumeration.ProductCategory;

import java.util.List;

public interface ProductService {
    List<Product> getAllProduct();

    Product getProductByName(String name);

    List<Product> getProductsByCategory(ProductCategory category);

    Product addProduct(Product product);

    Product updateProduct(int id, Product product);

    void deleteProduct(int id);
}
