package com.cart.demo.model.dto.purchase;

import java.util.List;

public record PurchaseOrderResponse(Long id, List<PurchaseOrderProductResponse> products, float totalPrice){}
