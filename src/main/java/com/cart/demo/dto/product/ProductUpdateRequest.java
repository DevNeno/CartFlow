package com.cart.demo.dto.product;

import com.cart.demo.model.enumeration.Category;

public record ProductUpdateRequest(
        String name,
        String description,
        float price,
        Category category
) {
}