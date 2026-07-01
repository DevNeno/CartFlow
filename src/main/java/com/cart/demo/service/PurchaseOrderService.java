package com.cart.demo.service;

import com.cart.demo.model.dto.purchase.PurchaseOrderResponse;

public interface PurchaseOrderService {
    PurchaseOrderResponse findById(Long id);
    PurchaseOrderResponse purchase(Long cartId);
}
