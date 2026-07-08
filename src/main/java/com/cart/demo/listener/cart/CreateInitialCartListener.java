package com.cart.demo.listener.cart;

import com.cart.demo.event.user.CreateInitialCartEvent;
import com.cart.demo.service.CartService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CreateInitialCartListener {
    private final CartService cartService;

    public CreateInitialCartListener(CartService cartService) {
        this.cartService = cartService;
    }

    @EventListener
    public void onApplicationEvent(CreateInitialCartEvent createInitialCartEvent) {
        cartService.createCart(createInitialCartEvent.userId());
    }
}
