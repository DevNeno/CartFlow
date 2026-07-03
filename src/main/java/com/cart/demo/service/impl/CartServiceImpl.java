package com.cart.demo.service.impl;

import com.cart.demo.exception.UserAlreadyHasActiveCart;
import com.cart.demo.mediator.CartProductMediator;
import com.cart.demo.model.dto.cart.CartProductRequest;
import com.cart.demo.model.dto.cart.CartProductQuantityResponse;
import com.cart.demo.model.dto.cart.CartResponse;
import com.cart.demo.model.entity.Cart;
import com.cart.demo.model.entity.CartProductInfo;
import com.cart.demo.model.entity.CartProductQuantity;
import com.cart.demo.repository.CartProductQuantityRepository;
import com.cart.demo.repository.CartRepository;
import com.cart.demo.service.CartService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cart.demo.model.enumeration.CartStatus.ACTIVE;

@Service
public class CartServiceImpl implements CartService {


    ///
    ///
    ///
    /// PROBAR MEDIATOR, COMMITEAR SI FUNCIONA Y CONTINUAR DESACOPLAMIENTO
    ///
    ///
    ///
    private final CartProductMediator mediator;
    private final ApplicationEventPublisher eventPublisher;
    private final CartRepository cartRepository;
    private final CartProductQuantityRepository cartProductQuantityRepository;

    private final CartProductInfo productInfo = new CartProductInfo();

    public CartServiceImpl(CartProductMediator mediator, ApplicationEventPublisher eventPublisher, CartRepository cartRepository, CartProductQuantityRepository cartProductQuantityRepository) {
        this.mediator = mediator;
        this.eventPublisher = eventPublisher;
        this.cartRepository = cartRepository;
        this.cartProductQuantityRepository = cartProductQuantityRepository;
    }

    @Override
    public void setProductInfo(String name, float price) {
        productInfo.setName(name);
        productInfo.setPrice(price);
    }

    @Override
    public void createActiveCart(Long userId){
        Cart cart = cartRepository.findByUserIdAndStatus(userId, ACTIVE);

        if (cart == null){
            cart = new Cart(ACTIVE, userId);
            cartRepository.save(cart);
        }else{
            throw new UserAlreadyHasActiveCart();
        }
    }

    @Override
    public CartResponse findById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }


        return generateResponse(cart);
    }

    @Override
    public CartResponse addProduct(Long userId, CartProductRequest request) {
        Cart cart = cartRepository.findByUserIdAndStatus(userId, ACTIVE);
        if (cart == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // Check if product ID exists
        eventPublisher.publishEvent(request.productId());

        CartProductQuantity productQuantity = new CartProductQuantity(cart, request.productId(), request.quantity());
        CartProductQuantity savedCartProductQuantity = cartProductQuantityRepository.save(productQuantity);
        cart.getProducts().add(savedCartProductQuantity);
        cartRepository.save(cart);

        return generateResponse(cart);
    }

    @Override
    public CartResponse updateProduct(Long userId, CartProductRequest request){
        Cart cart = cartRepository.findByUserIdAndStatus(userId, ACTIVE);
        if (cart == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return generateResponse(cart);
    }

    @Override
    public CartResponse deleteProduct(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserIdAndStatus(userId, ACTIVE);
        if (cart == null) {
            throw new ResourceNotFoundException("User not found");
        }

        int listSize =  cart.getProducts().size();
        int count = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {
            count++;
            if (cartProductQuantity.getProductId().equals(productId)) {
                cart.getProducts().remove(cartProductQuantity);
                cartProductQuantityRepository.deleteById(productId);
                break;
            }
            if (count == listSize){
                throw new ResourceNotFoundException("Product not found");
            }
        }
        cartRepository.save(cart);

        return generateResponse(cart);
    }

    @Override
    public CartResponse getCurrentCart(Long userId) {
        Cart cart = cartRepository.findByUserIdAndStatus(userId, ACTIVE);

        if (cart == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return generateResponse(cart);
    }

    private CartResponse generateResponse(Cart cart){
        List<CartProductQuantityResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {
            mediator.findProductInfoById(cartProductQuantity.getProductId());

            CartProductQuantityResponse cartProductQuantityResponse = new CartProductQuantityResponse(
                    productInfo.getName(),
                    productInfo.getPrice(),
                    cartProductQuantity.getQuantity()
            );
            productResponseList.add(cartProductQuantityResponse);
            totalPrice = totalPrice + cartProductQuantity.getQuantity() * productInfo.getPrice();
        }
        return new CartResponse(cart.getStatus(), productResponseList, totalPrice);
    }

}