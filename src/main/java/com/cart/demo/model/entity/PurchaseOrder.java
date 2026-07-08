package com.cart.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchaseOrder")
@Getter
public class PurchaseOrder{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    private float total_price;
    private final LocalDateTime date;

    @Setter
    @OneToMany(mappedBy = "purchase")
    private List<PurchaseProductQuantity> orderProducts = new ArrayList<>();

    @Setter
    private Long userId;

    public PurchaseOrder(){
        this.date = LocalDateTime.now();
    }
}