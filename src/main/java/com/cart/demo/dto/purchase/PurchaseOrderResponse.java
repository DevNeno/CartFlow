package com.cart.demo.dto.purchase;

import java.util.List;

public record PurchaseOrderResponse(Long id, List<PurchaseOrderProductResponse> products, float totalPrice){}
