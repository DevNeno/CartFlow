package com.cart.demo.service;

import com.cart.demo.dto.user.UserResponse;
import com.cart.demo.dto.user.UserSaveRequest;
import com.cart.demo.dto.user.UserUpdateRequest;

import java.util.List;

public interface UserService {
    public List<UserResponse> findAll();
    public UserResponse findById(Long id);
    public UserResponse save(UserSaveRequest request);
    public UserResponse update(Long id, UserUpdateRequest request);
    public void deleteById(Long id);
}