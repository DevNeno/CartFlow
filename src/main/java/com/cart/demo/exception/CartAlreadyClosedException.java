package com.cart.demo.exception;

public class CartAlreadyClosedException extends RuntimeException{
    public CartAlreadyClosedException() {
        super("Cart is already closed");
    }
}
