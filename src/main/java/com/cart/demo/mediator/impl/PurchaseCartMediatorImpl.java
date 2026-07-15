package com.cart.demo.mediator.impl;

import com.cart.demo.mediator.PurchaseCartMediator;
import com.cart.demo.service.CartService;
import com.cart.demo.service.PurchaseOrderService;
import org.springframework.stereotype.Component;

@Component
public class PurchaseCartMediatorImpl implements PurchaseCartMediator {

    private final CartService cartService;
    private final PurchaseOrderService purchaseOrderService;

    public PurchaseCartMediatorImpl(CartService cartService, PurchaseOrderService purchaseOrderService) {
        this.cartService = cartService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Override
    public void getCartIdByUserId(Long userId) {
        cartService.findByUserId(userId);
    }

    @Override
    public void returnCartId(Long cartId){
        purchaseOrderService.addCartId(cartId);
    }

    @Override
    public void findByIdMediatorPurchase(Long cartId, int listIndex) {
        cartService.findByIdMediatorPurchase(cartId, listIndex);
    }

    @Override
    public void returnCartProductInfoById(Long id, String name, float price, int quantity) {
        purchaseOrderService.addProductInfo(id, name, price, quantity);
    }
}
