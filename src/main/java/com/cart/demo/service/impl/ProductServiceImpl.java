package com.cart.demo.service.impl;

import com.cart.demo.dto.product.ProductRequest;
import com.cart.demo.dto.product.ProductResponse;
import com.cart.demo.model.entity.Product;
import com.cart.demo.repository.ProductRepository;
import com.cart.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            ProductResponse productResponse = new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategory()
            );
            productResponses.add(productResponse);
        }
        return productResponses;
    }

    @Override
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found");
        }
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory()
        );
    }

    @Override
    public ProductResponse save(ProductRequest request) {
        Product product = new Product(
                request.name(),
                request.description(),
                request.price(),
                request.category()
        );

        Product savedProduct = productRepository.save(product);

        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getCategory()
        );
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found");
        }
        if (request.name() != null && request.name().trim() != "") {
            product.setName(request.name());
        }
        if (request.description() != null && request.description().trim() != "") {
            product.setDescription(request.description());
        }
        if (/*request.price() != null &&*/ request.price() > 0){
            product.setPrice(request.price());
        }
        if ( request.category() != null){
            product.setCategory(request.category());
        }

        Product productUpdated = productRepository.save(product);
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory()
        );
    }

    @Override
    public void deleteById(Long id) {
        if(productRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
}