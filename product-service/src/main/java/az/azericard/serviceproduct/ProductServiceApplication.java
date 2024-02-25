package az.azericard.serviceproduct;

import az.azericard.serviceproduct.domain.entity.Product;
import az.azericard.serviceproduct.domain.enumeration.ProductAttributes;
import az.azericard.serviceproduct.domain.enumeration.ProductCategory;
import az.azericard.serviceproduct.repository.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class ProductServiceApplication implements ApplicationRunner {
    private final ProductRepository repository;

    public ProductServiceApplication(ProductRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Optional<Product> productByName = repository.findByName("Lenovo");
        if (productByName.isEmpty()) {
            var lenovo = new Product();
            lenovo.setName("Lenovo");
            lenovo.setCategory(ProductCategory.LAPTOP);
            lenovo.setQuantityInStock(23);
            lenovo.setPrice(new BigDecimal("2500"));

            Map<ProductAttributes, String> lenovoAttributes = Map.of(
                    ProductAttributes.RAM, "16",
                    ProductAttributes.SSD, "256");

            lenovo.setProductAttributes(lenovoAttributes);

            repository.save(lenovo);
        }

        productByName = repository.findByName("Iphone 15");
        if (productByName.isEmpty()) {
            var iphone = new Product();
            iphone.setName("Iphone 15");
            iphone.setCategory(ProductCategory.SMARTPHONE);
            iphone.setQuantityInStock(5);
            iphone.setPrice(new BigDecimal("3000"));

            Map<ProductAttributes, String> iphoneAttributes = Map.of(ProductAttributes.RAM, "6");
            iphone.setProductAttributes(iphoneAttributes);

            repository.save(iphone);
        }
    }
}
