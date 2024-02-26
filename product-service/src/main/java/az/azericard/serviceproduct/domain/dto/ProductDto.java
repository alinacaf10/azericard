package az.azericard.serviceproduct.domain.dto;

import az.azericard.serviceproduct.domain.entity.Product;
import az.azericard.serviceproduct.domain.enumeration.ProductAttributes;
import az.azericard.serviceproduct.domain.enumeration.ProductCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDto {

    @NotBlank(message = "{field.notBlank}")
    private String name;

    @NotBlank(message = "{field.notBlank}")
    private ProductCategory category;

    @NotBlank(message = "{field.notBlank}")
    private int quantityInStock;

    @NotBlank(message = "{field.notBlank}")
    private BigDecimal price;

    @NotBlank(message = "{field.notBlank}")
    private Map<ProductAttributes, String> productAttributes;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        setName(product.getName());
        setCategory(product.getCategory());
        setPrice(product.getPrice());
        setQuantityInStock(product.getQuantityInStock());
        setProductAttributes(product.getProductAttributes());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Map<ProductAttributes, String> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(Map<ProductAttributes, String> productAttributes) {
        this.productAttributes = productAttributes;
    }
}
