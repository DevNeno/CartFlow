package com.cart.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchaseOrder")
@Getter
@NoArgsConstructor
public class PurchaseOrder{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float total_price;
    private LocalDateTime date;
    @ManyToMany
    @JoinTable(
            name = "productPurchase",
            joinColumns = @JoinColumn(name = "IdProduct"),
            inverseJoinColumns = @JoinColumn(name = "IdPurchase")
    )
    private List<Product> products = new ArrayList<>();

    public PurchaseOrder(float total_price) {
        this.total_price = total_price;
        this.date = LocalDateTime.now();
    }
}