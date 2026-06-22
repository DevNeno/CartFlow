package com.cart.demo.dto.user;

public record UserResponse(
        Long id, String username, UserInfoResponse userInfo
) {}