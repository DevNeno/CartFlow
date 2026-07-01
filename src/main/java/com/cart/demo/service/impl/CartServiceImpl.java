package com.cart.demo.service.impl;

import com.cart.demo.dto.cart.CartProductRequest;
import com.cart.demo.dto.cart.CartProductQuantityResponse;
import com.cart.demo.dto.cart.CartResponse;
import com.cart.demo.model.entity.Cart;
import com.cart.demo.model.entity.CartProductQuantity;
import com.cart.demo.model.entity.Product;
import com.cart.demo.model.enumeration.CartStatus;
import com.cart.demo.repository.CartProductQuantityRepository;
import com.cart.demo.repository.CartRepository;
import com.cart.demo.repository.ProductRepository;
import com.cart.demo.service.CartService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartProductQuantityRepository cartProductQuantityRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository,  CartProductQuantityRepository cartProductQuantityRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartProductQuantityRepository = cartProductQuantityRepository;
    }

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
    public CartResponse addProduct(Long id, CartProductRequest request) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        if (cart.getStatus().equals(CartStatus.ARCHIEVED) || cart.getStatus().equals(CartStatus.ABANDONED)){
            throw new IllegalStateException("Cannot delete product");
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
    public CartResponse updateProduct(Long id, CartProductRequest request){
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        if (cart.getStatus().equals(CartStatus.ARCHIEVED) || cart.getStatus().equals(CartStatus.ABANDONED)){
            throw new IllegalStateException("Cannot delete product");
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
    public CartResponse deleteProduct(Long id, Long productId) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        if (cart.getStatus().equals(CartStatus.ARCHIEVED) || cart.getStatus().equals(CartStatus.ABANDONED)){
            throw new IllegalStateException("Cannot delete product");
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
    public void delete(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }
        cartRepository.delete(cart);
    }

}
