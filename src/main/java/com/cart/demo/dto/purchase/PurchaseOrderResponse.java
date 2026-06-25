package com.cart.demo.dto.purchase;

import com.cart.demo.dto.product.ProductQuantityResponse;

import java.util.List;

public record PurchaseOrderResponse(Long id, List<ProductQuantityResponse> products, float totalPrice){}
