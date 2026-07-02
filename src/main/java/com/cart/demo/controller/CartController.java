package com.cart.demo.controller;

import com.cart.demo.model.dto.cart.CartProductRequest;
import com.cart.demo.model.dto.cart.CartResponse;
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

    @PostMapping("/user/{userId}")
    public ResponseEntity<CartResponse> addProductToCart(@PathVariable Long userId, @Valid @RequestBody CartProductRequest request){
        return new ResponseEntity<>(cartService.addProduct(userId, request), HttpStatus.OK);
    }

    @PatchMapping("/user/{userId}")
    public ResponseEntity<CartResponse> updateProduct(@PathVariable Long userId, @Valid @RequestBody CartProductRequest request){
        return new ResponseEntity<>(cartService.updateProduct(userId, request), HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/products/{productId}")
    public ResponseEntity<CartResponse> removeProductFromCart(@PathVariable Long userId, @PathVariable Long productId){
        return new ResponseEntity<>(cartService.deleteProduct(userId, productId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponse> getCurrentCartFromClient(@PathVariable Long userId){
        return new ResponseEntity<>(cartService.getCurrentCart(userId), HttpStatus.OK);
    }
}
