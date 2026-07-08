package com.cart.demo.mediator;

public interface PurchaseCartMediator {
    void getUserIdByCartId(Long cartId);
    void returnUserId(Long userId);
    void findByIdMediatorPurchase(Long cartId, int listIndex);
    void returnCartProductInfoById(Long id, String name, float price, int quantity);
}
