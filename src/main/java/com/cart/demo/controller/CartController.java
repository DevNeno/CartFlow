package com.cart.demo.controller;

import com.cart.demo.dto.cart.CartProductRequest;
import com.cart.demo.dto.cart.CartResponse;
import com.cart.demo.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/cart")
public class CartController
{
    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getById(@PathVariable Long id){
        return new ResponseEntity<>(cartService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CartResponse> addProductToCart(@PathVariable Long id, @Valid @RequestBody CartProductRequest request){
        return new ResponseEntity<>(cartService.addProduct(id, request), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CartResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody CartProductRequest request){
        return new ResponseEntity<>(cartService.updateProduct(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/products/{productId}")
    public ResponseEntity<CartResponse> removeProductFromCart(@PathVariable Long id, @PathVariable Long productId){
        return new ResponseEntity<>(cartService.deleteProduct(id, productId), HttpStatus.OK);
    }
}
