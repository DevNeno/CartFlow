package com.cart.demo.service;

import com.cart.demo.model.dto.cart.CartProductRequest;
import com.cart.demo.model.dto.cart.CartResponse;

public interface CartService {
    CartResponse findById(Long id);
    CartResponse addProduct(Long id, CartProductRequest request);
    CartResponse updateProduct(Long id, CartProductRequest request);
    CartResponse deleteProduct(Long id, Long productId);
    void delete(Long id);
}
