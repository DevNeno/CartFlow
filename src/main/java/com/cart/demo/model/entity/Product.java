package com.cart.demo.model.entity;

import com.cart.demo.model.enumeration.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private float price;
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany(mappedBy = "products")
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<CartProductQuantity> cartsProductQuantity = new ArrayList<>();

    public Product(String name, String description, float price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // Setters
    void setName(String name) {
        this.name = name;
    }
    void setDescription(String description) {
        this.description = description;
    }
    void setPrice(float price) {
        this.price = price;
    }
    void setCategory(Category category) {
        this.category = category;
    }
}