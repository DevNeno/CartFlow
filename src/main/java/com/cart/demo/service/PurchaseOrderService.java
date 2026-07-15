package com.cart.demo.service;

import com.cart.demo.model.dto.purchase.PurchaseOrderResponse;

public interface PurchaseOrderService {
    PurchaseOrderResponse findById(Long id);
    PurchaseOrderResponse purchase(Long cartId);
    void addProductInfo(Long id, String name, float price, int quantity);


    // Mediator PurchaseCartMediator
     void addCartId(Long cartId);
}
