package com.cart.demo.exception;

public class UserAlreadyHasActiveCart extends RuntimeException {
    public UserAlreadyHasActiveCart() {
        super("User already has active cart");
    }
}
