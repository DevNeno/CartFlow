package com.cart.demo.model.dto.product;

import com.cart.demo.model.enumeration.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductSaveRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @Positive
        float price,
        @Positive
        int stock,
        @NotNull
        Category category
) {}