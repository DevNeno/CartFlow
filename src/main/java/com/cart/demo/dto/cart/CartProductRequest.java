package com.cart.demo.dto.cart;

import jakarta.validation.constraints.Positive;

public record CartProductRequest(
        @Positive
        Long productId,
        @Positive
        int quantity) {}
