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

    private Long productId;
    private String productName;

    // Setters
    @Setter
    private int quantity;

    @Setter
    private float unitPrice;

    public PurchaseProductQuantity(PurchaseOrder purchase, Long productId, String name, int quantity, float unitPrice) {
        this.purchase = purchase;
        this.productId = productId;
        this.productName = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

}