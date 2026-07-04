package com.cart.demo.exception;

public class UserAlreadyHasActiveCartException extends RuntimeException {
    public UserAlreadyHasActiveCartException() {
        super("User already has active cart");
    }
}
