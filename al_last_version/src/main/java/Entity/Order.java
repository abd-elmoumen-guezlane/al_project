package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "moumen_order", schema = "app")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String name;

    @Column(name = "customer_email", nullable = false)
    private String email;

    @Column(name = "customer_address", nullable = false)
    private String address;

    
    @Column(name = "total", nullable = false)
    private double total;

    public Order() {
    }

    public Order(String name, String email, String address, double total) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.total = total;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}