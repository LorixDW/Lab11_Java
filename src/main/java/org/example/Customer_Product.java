package org.example;

import javax.persistence.*;

@Entity
@Table(name = "customer_product")
public class Customer_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buy_id", nullable = false)
    private int buy_id;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "p_id")
    private Product product;
    @Column(name = "value")
    private int value;

    public Customer_Product(){}

    public int getBuy_id() {
        return buy_id;
    }
    public int getValue() {
        return value;
    }

    public void setBuy_id(int buy_id) {
        this.buy_id = buy_id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
}
