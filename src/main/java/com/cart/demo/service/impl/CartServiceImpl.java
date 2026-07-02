package com.cart.demo.service.impl;

import com.cart.demo.model.dto.cart.CartProductRequest;
import com.cart.demo.model.dto.cart.CartProductQuantityResponse;
import com.cart.demo.model.dto.cart.CartResponse;
import com.cart.demo.exception.CartAlreadyClosedException;
import com.cart.demo.model.entity.Cart;
import com.cart.demo.model.entity.CartProductQuantity;
import com.cart.demo.model.entity.Product;
import com.cart.demo.model.entity.User;
import com.cart.demo.model.enumeration.CartStatus;
import com.cart.demo.repository.CartProductQuantityRepository;
import com.cart.demo.repository.CartRepository;
import com.cart.demo.repository.ProductRepository;
import com.cart.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cart.demo.model.enumeration.CartStatus.ACTIVE;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartProductQuantityRepository cartProductQuantityRepository;


    @Override
    public CartResponse findById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        List<CartProductQuantityResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {

            CartProductQuantityResponse cartProductQuantityResponse = new CartProductQuantityResponse(
                    cartProductQuantity.getProduct().getName(),
                    cartProductQuantity.getProduct().getPrice(),
                    cartProductQuantity.getQuantity()
            );
            productResponseList.add(cartProductQuantityResponse);
            totalPrice = totalPrice + cartProductQuantity.getQuantity() * cartProductQuantity.getProduct().getPrice();
        }
       return new CartResponse(cart.getStatus(), productResponseList, totalPrice);
    }

    @Override
    public CartResponse addProduct(Long userId, CartProductRequest request) {
        Cart cart = cartRepository.findByClientIdAndStauts(userId, ACTIVE);
        if (cart == null) {
            throw new ResourceNotFoundException("User not found");
        }

        Product product = productRepository.findById(request.productId()).orElse(null);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found");
        }

        CartProductQuantity productQuantity = new CartProductQuantity(cart, product, request.quantity());
        CartProductQuantity savedCartProductQuantity = cartProductQuantityRepository.save(productQuantity);
        cart.getProducts().add(savedCartProductQuantity);
        cartRepository.save(cart);

        List<CartProductQuantityResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {

            CartProductQuantityResponse cartProductQuantityResponse = new CartProductQuantityResponse(
                    cartProductQuantity.getProduct().getName(),
                    cartProductQuantity.getProduct().getPrice(),
                    cartProductQuantity.getQuantity()
            );
            productResponseList.add(cartProductQuantityResponse);
            totalPrice = totalPrice + cartProductQuantity.getQuantity() * cartProductQuantity.getProduct().getPrice();
        }
        return new CartResponse(cart.getStatus(), productResponseList, totalPrice);
    }

    @Override
    public CartResponse updateProduct(Long userId, CartProductRequest request){
        Cart cart = cartRepository.findByClientIdAndStauts(userId, ACTIVE);
        if (cart == null) {
            throw new ResourceNotFoundException("User not found");
        }

        List<CartProductQuantityResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {

            if (request.productId().equals(cartProductQuantity.getProduct().getId())) {
                cartProductQuantity.setQuantity(request.quantity());
                cartProductQuantityRepository.save(cartProductQuantity);
            }

            CartProductQuantityResponse cartProductQuantityResponse = new CartProductQuantityResponse(
                    cartProductQuantity.getProduct().getName(),
                    cartProductQuantity.getProduct().getPrice(),
                    cartProductQuantity.getQuantity()
            );
            productResponseList.add(cartProductQuantityResponse);
            totalPrice = totalPrice + cartProductQuantity.getQuantity() * cartProductQuantity.getProduct().getPrice();
        }
        // Save change
        cartRepository.save(cart);

        return new CartResponse(cart.getStatus(), productResponseList, totalPrice);
    }

    @Override
    public CartResponse deleteProduct(Long userId, Long productId) {
        Cart cart = cartRepository.findByClientIdAndStauts(userId, ACTIVE);
        if (cart == null) {
            throw new ResourceNotFoundException("User not found");
        }

        int listSize =  cart.getProducts().size();
        int count = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {
            count++;
            if (cartProductQuantity.getProduct().getId().equals(productId)) {
                cart.getProducts().remove(cartProductQuantity);
                cartProductQuantityRepository.deleteById(productId);
                break;
            }
            if (count == listSize){
                throw new ResourceNotFoundException("Product not found");
            }
        }
        cartRepository.save(cart);

        List<CartProductQuantityResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {
            CartProductQuantityResponse cartProductQuantityResponse = new CartProductQuantityResponse(
                    cartProductQuantity.getProduct().getName(),
                    cartProductQuantity.getProduct().getPrice(),
                    cartProductQuantity.getQuantity()
            );
            productResponseList.add(cartProductQuantityResponse);
            totalPrice = totalPrice + cartProductQuantity.getQuantity() * cartProductQuantity.getProduct().getPrice();
        }
        return new CartResponse(cart.getStatus(), productResponseList, totalPrice);
    }

    @Override
    public CartResponse getCurrentCart(Long userId) {
        Cart cart = cartRepository.findByClientIdAndStauts(userId, ACTIVE);

        if (cart == null) {
            throw new ResourceNotFoundException("User not found");
        }

        List<CartProductQuantityResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {

            CartProductQuantityResponse cartProductQuantityResponse = new CartProductQuantityResponse(
                    cartProductQuantity.getProduct().getName(),
                    cartProductQuantity.getProduct().getPrice(),
                    cartProductQuantity.getQuantity()
            );
            productResponseList.add(cartProductQuantityResponse);
            totalPrice = totalPrice + cartProductQuantity.getQuantity() * cartProductQuantity.getProduct().getPrice();
        }
        return new CartResponse(cart.getStatus(), productResponseList, totalPrice);

    }

}
