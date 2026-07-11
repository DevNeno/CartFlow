package com.cart.demo.mediator.impl;

import com.cart.demo.mediator.PurchaseProductMediator;
import com.cart.demo.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class PurchaseProductMediatorImpl implements PurchaseProductMediator {

    private final ProductService productService;

    public PurchaseProductMediatorImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void deductStock(Long productId, int quantity) {
        productService.deductStock(productId, quantity);
    }
}
