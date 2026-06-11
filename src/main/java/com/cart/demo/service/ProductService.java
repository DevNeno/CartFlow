package com.cart.demo.service;

import com.cart.demo.dto.product.ProductRequest;
import com.cart.demo.dto.product.ProductResponse;

import java.util.List;


public interface ProductService {

    public List<ProductResponse>  findAll();
    public ProductResponse findById(Long id);
    public ProductResponse save(ProductRequest productRequest);
    public ProductResponse update(Long id, ProductRequest productRequest);
    public void deleteById(Long id);

}