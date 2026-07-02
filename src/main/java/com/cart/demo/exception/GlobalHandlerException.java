package com.cart.demo.exception;


import com.cart.demo.model.payload.ApiResponse;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartAlreadyClosedException.class)
    public ResponseEntity<ApiResponse>  cartAlreadyClosed() {
        ApiResponse apiResponse = new ApiResponse("Cart is already closed");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
