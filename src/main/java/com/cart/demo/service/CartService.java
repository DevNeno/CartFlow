package com.cart.demo.service;

import com.cart.demo.model.dto.cart.CartProductRequest;
import com.cart.demo.model.dto.cart.CartResponse;

public interface CartService {
    void setProductInfo(String name, float price);
    CartResponse findById(Long id);
    void createCart(Long userId);
    CartResponse addProduct(Long userId, CartProductRequest request);
    CartResponse updateProduct(Long userId, CartProductRequest request);
    CartResponse deleteProduct(Long userId, Long productId);
    CartResponse getCurrentCart(Long userId);
    void archiveCart(Long id);

    // Mediator PurchaseCartMediator
    void findUserId(Long id);
    void findByIdMediatorPurchase(Long id, int listIndex);

    // Mediator CartProductMediator
    void findByIdMediator(Long id);
}
