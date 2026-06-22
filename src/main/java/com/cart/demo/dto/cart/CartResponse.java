package com.cart.demo.dto.cart;

import com.cart.demo.dto.product.ProductResponse;
import com.cart.demo.model.enumeration.CartStatus;

import java.util.List;

public record CartResponse(CartStatus status, List<CartProductResponse> products, float totalPrice){}
