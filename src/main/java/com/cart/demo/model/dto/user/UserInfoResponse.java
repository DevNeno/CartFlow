package com.cart.demo.model.dto.user;

public record UserInfoResponse(
        Long id, String firstname,
        String lastname, String city, String country
) {}