package com.cart.demo.repository;

import com.cart.demo.model.entity.Cart;
import com.cart.demo.model.enumeration.CartStatus;
import org.springframework.data.repository.ListCrudRepository;

public interface CartRepository extends ListCrudRepository<Cart, Long> {
    Cart findByUserIdAndStatus(Long clientId, CartStatus status);
}