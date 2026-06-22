package com.cart.demo.repository;

import com.cart.demo.model.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long> {}
