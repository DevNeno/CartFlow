package com.cart.demo.listener.product;

import com.cart.demo.event.product.FindProductEvent;
import com.cart.demo.service.ProductService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FindProductListener {
    private final ProductService productService;

    public FindProductListener(ProductService productService) {
        this.productService = productService;
    }

    @EventListener
    public void onApplicationEvent(FindProductEvent event){
        productService.findById(event.productId());
    }
}
