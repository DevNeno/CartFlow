package com.cart.demo.model.entity;

import com.cart.demo.model.enumeration.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Setter
    private String name;
    @Setter
    private String description;
    @Setter
    private float price;

    @Setter
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<PurchaseProductQuantity> purchaseProductQuantity = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<CartProductQuantity> cartsCartProductQuantity = new ArrayList<>();

    public Product(String name, String description, float price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}