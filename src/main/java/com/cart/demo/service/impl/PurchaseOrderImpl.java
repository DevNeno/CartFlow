package com.cart.demo.service.impl;

import com.cart.demo.dto.purchase.PurchaseOrderProductResponse;
import com.cart.demo.dto.purchase.PurchaseOrderResponse;
import com.cart.demo.model.entity.Cart;
import com.cart.demo.model.entity.CartProductQuantity;
import com.cart.demo.model.entity.PurchaseOrder;
import com.cart.demo.model.entity.PurchaseProductQuantity;
import com.cart.demo.repository.CartRepository;
import com.cart.demo.repository.PurchaseOrderProductRepository;
import com.cart.demo.repository.PurchaseOrderRepository;
import com.cart.demo.service.PurchaseOrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cart.demo.model.enumeration.CartStatus.ARCHIEVED;

@Service
public class PurchaseOrderImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PurchaseOrderProductRepository purchaseOrderProductRepository;

    @Override
    public PurchaseOrderResponse findById(Long id) {
        PurchaseOrder purchaseOrder =  purchaseOrderRepository.findById(id).orElse(null);
        if (purchaseOrder == null) {
            throw new EntityNotFoundException("Purchase Order Not Found");
        }

        List<PurchaseOrderProductResponse> products = new ArrayList<>();
        for (PurchaseProductQuantity purchaseProductQuantity : purchaseOrder.getOrderProducts()){
            PurchaseOrderProductResponse purchaseOrderProductResponse = new PurchaseOrderProductResponse(
                    purchaseProductQuantity.getProduct().getName(),
                    purchaseProductQuantity.getProduct().getPrice(),
                    purchaseProductQuantity.getQuantity()
            );
            products.add(purchaseOrderProductResponse);
        }
        return new PurchaseOrderResponse(purchaseOrder.getId(), products, purchaseOrder.getTotal_price());
    }

    @Override
    public PurchaseOrderResponse purchase(Long cartId) {
        Cart cart =  cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new EntityNotFoundException("Cart Not Found");
        }
        PurchaseOrder purchase = purchaseOrderRepository.save(new PurchaseOrder());

        List<PurchaseProductQuantity> products = new ArrayList<>();
        float totalPrice = 0;

        List<PurchaseOrderProductResponse> responseProducts = new ArrayList<>();

        for (CartProductQuantity cartProductQuantity : cart.getProducts()) {
            PurchaseProductQuantity purchaseProduct = new PurchaseProductQuantity(
                    purchase,
                    cartProductQuantity.getProduct(),
                    cartProductQuantity.getQuantity(),
                    cartProductQuantity.getProduct().getPrice()
            );
            purchaseOrderProductRepository.save(purchaseProduct);
            products.add(purchaseProduct);
            totalPrice = totalPrice + cartProductQuantity.getQuantity() * cartProductQuantity.getProduct().getPrice();

            PurchaseOrderProductResponse purchaseOrderProductResponse = new PurchaseOrderProductResponse(
                    cartProductQuantity.getProduct().getName(),
                    cartProductQuantity.getProduct().getPrice(),
                    cartProductQuantity.getQuantity()
            );
            responseProducts.add(purchaseOrderProductResponse);
        }
        purchase.setOrderProducts(products);
        purchase.setTotal_price(totalPrice);
        purchaseOrderRepository.save(purchase);

        cart.setStatus(ARCHIEVED);
        cartRepository.save(cart);

        return new PurchaseOrderResponse(purchase.getId(), responseProducts, totalPrice);
    }
}
