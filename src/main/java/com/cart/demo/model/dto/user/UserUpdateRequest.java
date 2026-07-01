package com.cart.demo.model.dto.user;

public record UserUpdateRequest(
        String username,  String password,
        String firstname, String lastname,
        String city, String country
) {}