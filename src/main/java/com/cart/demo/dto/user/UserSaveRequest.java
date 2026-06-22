package com.cart.demo.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserSaveRequest(
        @NotBlank String username,  @NotBlank String password,
        @NotBlank String firstname, @NotBlank String lastname,
        @NotBlank String city, @NotBlank String country
) {}