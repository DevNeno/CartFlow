package com.cart.demo.listener.cart;

import com.cart.demo.event.purchaseOrder.ArchiveCartEvent;
import com.cart.demo.service.CartService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ArchiveCartListener {
    private final CartService cartService;

    public ArchiveCartListener(CartService cartService) {
        this.cartService = cartService;
    }

    @EventListener
    public void onApplicationEvent(ArchiveCartEvent event){
        cartService.archiveCart(event.cartId());
    }
}
