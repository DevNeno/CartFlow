package com.cart.demo.repository;

import com.cart.demo.model.entity.CartProductQuantity;
import org.springframework.data.repository.ListCrudRepository;

public interface CartProductQuantityRepository extends ListCrudRepository<CartProductQuantity, Long>{}
