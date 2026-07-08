package com.cart.demo.service;

import com.cart.demo.model.dto.user.UserResponse;
import com.cart.demo.model.dto.user.UserSaveRequest;
import com.cart.demo.model.dto.user.UserUpdateRequest;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(Long id);
    UserResponse save(UserSaveRequest request);
    UserResponse update(Long id, UserUpdateRequest request);
    void deleteById(Long id);

    // Events
    void linkPurchaseToUser(Long id, Long purchaseId);
}