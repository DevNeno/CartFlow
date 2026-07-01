package com.cart.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Setter
    private float total_price;
    private LocalDateTime date;

    @Setter
    @OneToMany(mappedBy = "purchase")
    private List<PurchaseProductQuantity> orderProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_purchaseOrder")
    private User user;

    public PurchaseOrder(float total_price, List<PurchaseProductQuantity> products) {
        this.total_price = total_price;
        this.orderProducts = products;
        this.date = LocalDateTime.now();
    }
}