package com.cart.demo.mediator;

public interface CartProductMediator {
    void findProductInfoById(Long productId);
    void returnProductInfoById(String name, float price);
}
