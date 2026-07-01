package com.cart.demo.repository;

import com.cart.demo.model.entity.PurchaseProductQuantity;
import org.springframework.data.repository.ListCrudRepository;

public interface PurchaseOrderProductRepository extends ListCrudRepository<PurchaseProductQuantity, Long> {
}
