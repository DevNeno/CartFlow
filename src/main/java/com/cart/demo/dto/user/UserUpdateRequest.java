package com.cart.demo.dto.user;

public record UserUpdateRequest(
        String username,  String password,
        String firstname, String lastname,
        String city, String country
) {}