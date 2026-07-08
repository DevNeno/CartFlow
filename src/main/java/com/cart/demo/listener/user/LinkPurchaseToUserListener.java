package com.cart.demo.listener.user;

import com.cart.demo.event.purchaseOrder.LinkPurchaseToUserEvent;
import com.cart.demo.service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LinkPurchaseToUserListener {
    private final UserService userService;

    public LinkPurchaseToUserListener(UserService userService) {
        this.userService = userService;
    }

    @EventListener
    @Async
    public void onApplicationEvent(LinkPurchaseToUserEvent event){
        userService.linkPurchaseToUser(event.userId(), event.purchaseId());
    }
}
