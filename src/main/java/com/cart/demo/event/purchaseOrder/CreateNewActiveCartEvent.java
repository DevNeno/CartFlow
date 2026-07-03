package com.cart.demo.event.purchaseOrder;

import org.springframework.context.ApplicationEvent;

public class CreateNewActiveCartEvent extends ApplicationEvent {
    public CreateNewActiveCartEvent(Long userId) {
        super(userId);
    }
}
