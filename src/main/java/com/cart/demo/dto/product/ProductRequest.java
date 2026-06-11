package com.cart.demo.dto.product;

import com.cart.demo.model.enumeration.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @Positive
        float price,
        @NotNull
        Category category
){}