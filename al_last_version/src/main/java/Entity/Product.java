package Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCT", schema = "app")
@NamedQuery(name = "Product.findAllOrdered", query = "SELECT p FROM Product p ORDER BY p.id")
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "PURCHASE_COST")
    private double purchaseCost;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "MANUFACTURER_ID")
    private Long manufacturerId;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "QUANTITY_ON_HAND")
    private Integer quantityOnHand;

    @Column(name = "MARKUP")
    private double markup;

    @Column(name = "AVAILABLE")
    private boolean available;

    public Long getId() {
        return id;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public String getDescription() {
        return description;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public String getProductCode() {
        return productCode;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public double getMarkup() {
        return markup;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getSellingPrice() {
        return purchaseCost + (purchaseCost * markup / 100);
    }
}
