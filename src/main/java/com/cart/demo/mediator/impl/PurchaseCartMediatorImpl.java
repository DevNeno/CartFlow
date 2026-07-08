package com.cart.demo.mediator.impl;

import com.cart.demo.mediator.PurchaseCartMediator;
import com.cart.demo.service.CartService;
import com.cart.demo.service.PurchaseOrderService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
    public void getUserIdByCartId(Long cartId) {
        cartService.findUserId(cartId);
    }

    @Override
    public void returnUserId(Long userId){
        purchaseOrderService.addUserId(userId);
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
