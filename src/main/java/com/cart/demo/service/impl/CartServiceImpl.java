package com.cart.demo.service.impl;

import com.cart.demo.event.product.FindProductEvent;
import com.cart.demo.mediator.CartProductMediator;
import com.cart.demo.mediator.PurchaseCartMediator;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cart.demo.model.enumeration.CartStatus.ACTIVE;
import static com.cart.demo.model.enumeration.CartStatus.ARCHIEVED;

@Service
public class CartServiceImpl implements CartService {

    private final PurchaseCartMediator purchaseCartMediator;
    private final CartProductMediator cartProductMediator;
    private final ApplicationEventPublisher eventPublisher;
    private final CartRepository cartRepository;
    private final CartProductQuantityRepository cartProductQuantityRepository;

    private final CartProductInfo productInfo = new CartProductInfo();

    public CartServiceImpl(@Lazy PurchaseCartMediator purchaseCartMediator , @Lazy CartProductMediator cartProductMediator, ApplicationEventPublisher eventPublisher, CartRepository cartRepository, CartProductQuantityRepository cartProductQuantityRepository) {
        this.purchaseCartMediator = purchaseCartMediator;
        this.cartProductMediator = cartProductMediator;
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
    public CartResponse findById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        return generateResponse(cart);
    }

    @Override
    public void createCart(Long userId){
        cartRepository.save(new Cart(ACTIVE, userId));
    }


    // Used by CartProductMediator
    @Override
    public void findByIdMediator(Long id){
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }
    }


    @Override
    public CartResponse addProduct(Long userId, CartProductRequest request) {
        Cart cart = cartRepository.findByUserIdAndStatus(userId, ACTIVE);
        if (cart == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // Check if product ID exists
        eventPublisher.publishEvent(new FindProductEvent(request.productId()));

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

        int listSize =  cart.getProducts().size();
        int count = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {
            count++;
            if (cartProductQuantity.getProductId().equals(request.productId())) {
                cartProductQuantity.setQuantity(request.quantity());
                break;
            }
            if (count == listSize){
                throw new ResourceNotFoundException("Product not found");
            }
        }
        Cart updatedCart = cartRepository.save(cart);
        return generateResponse(updatedCart);
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

    // Used by event ArchiveCartEvent
    @Override
    public void archiveCart(Long id){
        Cart cart = cartRepository.findById(id).orElse(null);

        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        cart.setStatus(ARCHIEVED);
        cartRepository.save(cart);

        createActiveCart(cart.getUserId());
    }


    // Used by PurchaseCartMediator
    @Override
    public void findUserId(Long id){
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null){
            throw new ResourceNotFoundException("Cart not found");
        }
        purchaseCartMediator.returnUserId(cart.getUserId());
    }

    // Used by PurchaseCartMediator
    @Override
    public void findByIdMediatorPurchase(Long cartId, int listIndex){
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }

        if (listIndex < cart.getProducts().size()){
            CartProductQuantity productQuantity = cart.getProducts().get(listIndex);

            cartProductMediator.findProductInfoById(productQuantity.getProductId());
            purchaseCartMediator.returnCartProductInfoById(
                    productQuantity.getId(),
                    productInfo.getName(),
                    productInfo.getPrice(),
                    productQuantity.getQuantity()
            );
        }else{
            purchaseCartMediator.returnCartProductInfoById((long)-1, "", 0, 0);
        }
    }


    private CartResponse generateResponse(Cart cart){
        List<CartProductQuantityResponse> productResponseList = new ArrayList<>();
        float totalPrice = 0;
        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {
            cartProductMediator.findProductInfoById(cartProductQuantity.getProductId());

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

    private void createActiveCart(Long userId){
        Cart cart = new Cart(ACTIVE, userId);
        cartRepository.save(cart);

    }

}