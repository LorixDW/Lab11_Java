package org.example;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int p_id;
    @Column(name = "product_name",nullable = false)
    private String name;
    @Column(name = "price",nullable = false)
    private int price;
    @OneToMany(mappedBy = "product")
    public List<Customer_Product> customer_product;
    public Product(){}
    public Product(String name, int price){
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return p_id;
    }

    public void setId(int id) {
        this.p_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @Override
    public  String toString(){
        return this.getName();
    }
}
