package com.cart.demo.service;

import com.cart.demo.dto.purchase.PurchaseOrderResponse;

public interface PurchaseOrderService {
    PurchaseOrderResponse findById(Long id);
    PurchaseOrderResponse purchase(Long cartId);
}
