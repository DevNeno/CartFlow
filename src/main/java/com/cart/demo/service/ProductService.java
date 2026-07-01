package com.cart.demo.service;

import com.cart.demo.model.dto.product.ProductResponse;
import com.cart.demo.model.dto.product.ProductSaveRequest;
import com.cart.demo.model.dto.product.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    List<ProductResponse>  findAll();
    ProductResponse findById(Long id);
    ProductResponse save(ProductSaveRequest request);
    ProductResponse update(Long id, ProductUpdateRequest request);
    void deleteById(Long id);
}