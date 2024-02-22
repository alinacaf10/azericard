package az.azericard.serviceproduct.service.impl;

import az.azericard.serviceproduct.domain.entity.Product;
import az.azericard.serviceproduct.domain.enumeration.ProductCategory;
import az.azericard.serviceproduct.repository.ProductRepository;
import az.azericard.serviceproduct.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product getProductByName(String name) {
        if (productRepository.getProductByName(name).isEmpty()) {
            throw new IllegalArgumentException("Product '" + name + "' not found!");
        }
        return productRepository.getProductByName(name).get();
    }

    @Override
    public List<Product> getProductsByCategory(ProductCategory category) {
//        if (productRepository.getProductsByCategory(category).isEmpty()) {
//            throw new IllegalArgumentException("Category not found!");
//        }
//        I'm not sure this state is true
        return productRepository.getProductsByCategory(category);
    }

    @Override
    @Transactional
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(int id, Product product) {
        return null;
    }

    @Override
    @Transactional
    public void deleteProduct(int id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        } else {
            productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id:" + id + " not found!"));
        }
    }
}
