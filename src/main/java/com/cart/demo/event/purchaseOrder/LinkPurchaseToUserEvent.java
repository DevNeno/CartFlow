package com.cart.demo.event.purchaseOrder;

public record LinkPurchaseToUserEvent(Long userId, Long purchaseId) {}
