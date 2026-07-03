package com.cart.demo.mediator.impl;

import com.cart.demo.mediator.CartProductMediator;
import com.cart.demo.service.CartService;
import com.cart.demo.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class CartProductMediatorImpl implements CartProductMediator {

    private final ProductService productService;
    private final CartService cartService;

    public CartProductMediatorImpl(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @Override
    public void findProductInfoById(Long productId) {
        productService.findByIdMediator(productId);
    }

    @Override
    public void returnProductInfoById(String name, float price) {
        cartService.setProductInfo(name,price);
    }
}
