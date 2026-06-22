package com.cart.demo.repository;

import com.cart.demo.model.entity.Cart;
import org.springframework.data.repository.ListCrudRepository;

public interface CartRepository extends ListCrudRepository<Cart, Long> {}