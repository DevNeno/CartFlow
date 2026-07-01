package com.cart.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchaseProductQuantity")

@NoArgsConstructor
@Getter
public class PurchaseProductQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseId")
    private PurchaseOrder purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    // Setters
    @Setter
    private int quantity;

    @Setter
    private float unitPrice;

    public PurchaseProductQuantity(PurchaseOrder purchase, Product product, int quantity, float unitPrice) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

}