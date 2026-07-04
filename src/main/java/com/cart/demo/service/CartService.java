package com.cart.demo.service;

import com.cart.demo.model.dto.cart.CartProductRequest;
import com.cart.demo.model.dto.cart.CartResponse;

public interface CartService {
    void setProductInfo(String name, float price);
    void createActiveCart(Long userId);
    void findByIdMediator(Long id);
    CartResponse findById(Long id);
    CartResponse addProduct(Long userId, CartProductRequest request);
    CartResponse updateProduct(Long userId, CartProductRequest request);
    CartResponse deleteProduct(Long userId, Long productId);
    CartResponse getCurrentCart(Long userId);
    void archiveCart(Long id);
}
