package com.cart.demo.model.dto.product;

import com.cart.demo.model.enumeration.Category;

public record ProductResponse(
        Long id,
        String name,
        String description,
        float price,
        Category category
){}