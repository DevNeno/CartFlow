package com.cart.demo.repository;

import com.cart.demo.model.entity.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Long> {}