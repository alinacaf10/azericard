package az.azericard.serviceproduct.domain.entity;

import az.azericard.serviceproduct.domain.enumeration.ProductAttributes;
import az.azericard.serviceproduct.domain.enumeration.ProductCategory;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private ProductCategory category;

    private int quantityInStock;

    private BigDecimal price;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_attributes")
    @MapKeyColumn(name = "attribute_key")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyClass(ProductAttributes.class)
    @Column(name = "attribute_value")
    private Map<ProductAttributes, String> productAttributes;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
