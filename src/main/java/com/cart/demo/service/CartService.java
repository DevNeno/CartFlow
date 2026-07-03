package com.cart.demo.service;

import com.cart.demo.model.dto.cart.CartProductRequest;
import com.cart.demo.model.dto.cart.CartResponse;

public interface CartService {
    void createActiveCart(Long userId);
    CartResponse findById(Long id);
    CartResponse addProduct(Long userId, CartProductRequest request);
    CartResponse updateProduct(Long userId, CartProductRequest request);
    CartResponse deleteProduct(Long userId, Long productId);
    CartResponse getCurrentCart(Long userId);
}
