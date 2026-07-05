package com.cart.demo.mediator;

public interface PurchaseCartMediator {
    void findByIdMediatorPurchase(Long cartId, int listIndex);
    void returnCartInfoById(Long id, String name, float price, int quantity);
}
