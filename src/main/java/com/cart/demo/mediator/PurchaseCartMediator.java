package com.cart.demo.mediator;

public interface PurchaseCartMediator {
    void getCartIdByUserId(Long userId);
    void returnCartId(Long cartId);
    void findByIdMediatorPurchase(Long cartId, int listIndex);
    void returnCartProductInfoById(Long id, String name, float price, int quantity);
}
