package com.cart.demo.mediator;

public interface PurchaseProductMediator {
    void deductStock(Long productId, int quantity);
}
