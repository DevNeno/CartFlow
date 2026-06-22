package com.cart.demo.service.impl;

import com.cart.demo.dto.cart.CartProductRequest;
import com.cart.demo.dto.cart.CartProductResponse;
import com.cart.demo.dto.cart.CartResponse;
import com.cart.demo.model.entity.Cart;
import com.cart.demo.model.entity.CartProductQuantity;
import com.cart.demo.model.entity.Product;
import com.cart.demo.repository.CartRepository;
import com.cart.demo.repository.ProductRepository;
import com.cart.demo.service.CartService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartResponse findById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        List<CartProductResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity productQuantity : cart.getProducts()) {

            CartProductResponse cartProductResponse = new CartProductResponse(
                    productQuantity.getProduct().getName(),
                    productQuantity.getProduct().getPrice(),
                    productQuantity.getQuantity()
            );
            productResponseList.add(cartProductResponse);
            totalPrice = totalPrice + productQuantity.getQuantity() * productQuantity.getProduct().getPrice();
        }
       return new CartResponse(cart.getStatus(), productResponseList, totalPrice);
    }

    @Override
    public CartResponse addProduct(Long id, CartProductRequest request) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        Product product = productRepository.findById(request.productId()).orElse(null);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found");
        }

        CartProductQuantity productQuantity = new CartProductQuantity(cart, product, request.quantity());
        cart.getProducts().add(productQuantity);
        cartRepository.save(cart);

        List<CartProductResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {

            CartProductResponse cartProductResponse = new CartProductResponse(
                    cartProductQuantity.getProduct().getName(),
                    cartProductQuantity.getProduct().getPrice(),
                    cartProductQuantity.getQuantity()
            );
            productResponseList.add(cartProductResponse);
            totalPrice = totalPrice + cartProductQuantity.getQuantity() * cartProductQuantity.getProduct().getPrice();
        }
        return new CartResponse(cart.getStatus(), productResponseList, totalPrice);
    }

    @Override
    public CartResponse updateProduct(Long id, CartProductRequest request){
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        List<CartProductResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {

            if (request.productId().equals(cartProductQuantity.getProduct().getId())) {
                cartProductQuantity.setQuantity(request.quantity());
            }

            CartProductResponse cartProductResponse = new CartProductResponse(
                    cartProductQuantity.getProduct().getName(),
                    cartProductQuantity.getProduct().getPrice(),
                    cartProductQuantity.getQuantity()
            );
            productResponseList.add(cartProductResponse);
            totalPrice = totalPrice + cartProductQuantity.getQuantity() * cartProductQuantity.getProduct().getPrice();
        }
        // Save change
        cartRepository.save(cart);

        return new CartResponse(cart.getStatus(), productResponseList, totalPrice);
    }

    @Override
    public CartResponse deleteProduct(Long id, Long productId) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        int listSize =  cart.getProducts().size();
        int count = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {
            count++;
            if (cartProductQuantity.getProduct().getId().equals(productId)) {
                cart.getProducts().remove(cartProductQuantity);
                break;
            }
            if (count == listSize){
                throw new ResourceNotFoundException("Product not found");
            }
        }
        cartRepository.save(cart);

        List<CartProductResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {
            CartProductResponse cartProductResponse = new CartProductResponse(
                    cartProductQuantity.getProduct().getName(),
                    cartProductQuantity.getProduct().getPrice(),
                    cartProductQuantity.getQuantity()
            );
            productResponseList.add(cartProductResponse);
            totalPrice = totalPrice + cartProductQuantity.getQuantity() * cartProductQuantity.getProduct().getPrice();
        }
        return new CartResponse(cart.getStatus(), productResponseList, totalPrice);
    }


    @Override
    public void delete(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }
        cartRepository.delete(cart);
    }
}
