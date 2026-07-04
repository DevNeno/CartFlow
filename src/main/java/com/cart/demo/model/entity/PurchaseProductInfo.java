package com.cart.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PurchaseProductInfo {
    private Long id;
    private String name;
    private float price;
    private int quantity;
}
