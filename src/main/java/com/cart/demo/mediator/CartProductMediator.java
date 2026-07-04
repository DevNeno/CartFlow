package com.cart.demo.mediator;

import com.cart.demo.model.dto.mediator.ProductSummaryDTO;

public interface CartProductMediator {
    void findProductInfoById(Long productId);
    void returnProductInfoById(String name, float price);
    ProductSummaryDTO getProductSummaryById(Long productId);
}
