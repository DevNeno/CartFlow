package com.cart.demo.controller;


import com.cart.demo.model.dto.product.ProductResponse;
import com.cart.demo.model.dto.product.ProductSaveRequest;
import com.cart.demo.model.dto.product.ProductUpdateRequest;
import com.cart.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Get all
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }
    // Get by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id){
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    // Save
    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductSaveRequest request){
        return new ResponseEntity<>(productService.save(request), HttpStatus.CREATED);
    }
    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request){
        return new ResponseEntity<>(productService.update(id, request), HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
       productService.deleteById(id);
       return ResponseEntity.noContent().build();
    }

}