package com.cart.demo.model.dto.cart;

import com.cart.demo.model.enumeration.CartStatus;

import java.util.List;

public record CartResponse(CartStatus status, List<CartProductQuantityResponse> products, float totalPrice){}
