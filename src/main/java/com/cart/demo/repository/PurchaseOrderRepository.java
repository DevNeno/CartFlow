package com.cart.demo.repository;

import com.cart.demo.model.entity.PurchaseOrder;
import org.springframework.data.repository.ListCrudRepository;

public interface PurchaseOrderRepository extends ListCrudRepository<PurchaseOrder, Long> {}
