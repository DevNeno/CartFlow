package com.cart.demo.mediator;

public interface PurchaseCartMediator {
    void findByIdMediatorPurchase(Long cartId);
    void returnCartInfoById(Long id, String name, float price, int quantity);
}
