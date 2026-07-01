package com.cart.demo.model.dto.user;

public record UserResponse(
        Long id, String username, UserInfoResponse userInfo
) {}