package com.cart.demo.service.impl;

import com.cart.demo.event.purchaseOrder.ArchiveCartEvent;
import com.cart.demo.mediator.PurchaseCartMediator;
import com.cart.demo.model.dto.purchase.PurchaseOrderProductResponse;
import com.cart.demo.model.dto.purchase.PurchaseOrderResponse;
import com.cart.demo.model.entity.*;
import com.cart.demo.repository.PurchaseOrderProductRepository;
import com.cart.demo.repository.PurchaseOrderRepository;
import com.cart.demo.service.PurchaseOrderService;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderImpl implements PurchaseOrderService {

    private final PurchaseCartMediator purchaseCartMediator;
    private final ApplicationEventPublisher eventPublisher;

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderProductRepository purchaseOrderProductRepository;

    @Setter
    private PurchaseProductInfo productInfo = new PurchaseProductInfo();

    public PurchaseOrderImpl(@Lazy PurchaseCartMediator purchaseCartMediator, ApplicationEventPublisher eventPublisher, PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderProductRepository purchaseOrderProductRepository){
        this.purchaseCartMediator = purchaseCartMediator;
        this.eventPublisher = eventPublisher;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderProductRepository = purchaseOrderProductRepository;
    }

    @Override
    public PurchaseOrderResponse findById(Long id) {
        PurchaseOrder purchaseOrder =  purchaseOrderRepository.findById(id).orElse(null);
        if (purchaseOrder == null) {
            throw new ResourceNotFoundException("Purchase Order Not Found");
        }

        List<PurchaseOrderProductResponse> products = new ArrayList<>();
        for (PurchaseProductQuantity purchaseProductQuantity : purchaseOrder.getOrderProducts()){
            PurchaseOrderProductResponse purchaseOrderProductResponse = new PurchaseOrderProductResponse(
                    purchaseProductQuantity.getProductName(),
                    purchaseProductQuantity.getUnitPrice(),
                    purchaseProductQuantity.getQuantity()
            );
            products.add(purchaseOrderProductResponse);
        }
        return new PurchaseOrderResponse(purchaseOrder.getId(), products, purchaseOrder.getTotal_price());
    }

    @Override
    public PurchaseOrderResponse purchase(Long cartId) {
        PurchaseOrder purchase = purchaseOrderRepository.save(new PurchaseOrder());

        List<PurchaseProductQuantity> products = new ArrayList<>();
        List<PurchaseOrderProductResponse> responseProducts = new ArrayList<>();


        float totalPrice = 0;
        int listIndex = 0;
        while (true) {
            purchaseCartMediator.findByIdMediatorPurchase(cartId, listIndex);
            if(productInfo.getId() == -1){
                break;
            }
            PurchaseProductQuantity purchaseProduct = new PurchaseProductQuantity(
                    purchase,
                    productInfo.getId(),
                    productInfo.getName(),
                    productInfo.getQuantity(),
                    productInfo.getPrice()
            );
            purchaseOrderProductRepository.save(purchaseProduct);
            products.add(purchaseProduct);
            totalPrice = totalPrice + productInfo.getQuantity() * productInfo.getPrice();

            PurchaseOrderProductResponse purchaseOrderProductResponse = new PurchaseOrderProductResponse(
                    productInfo.getName(),
                    productInfo.getPrice(),
                    productInfo.getQuantity()
            );
            responseProducts.add(purchaseOrderProductResponse);
            listIndex++;
        }

        purchase.setOrderProducts(products);
        purchase.setTotal_price(totalPrice);
        purchaseOrderRepository.save(purchase);

        eventPublisher.publishEvent(new ArchiveCartEvent(cartId));

        return new PurchaseOrderResponse(purchase.getId(), responseProducts, totalPrice);
    }

    // Method used by mediator
    public void addProductInfo(Long id, String name, float price, int quantity){
        productInfo = new PurchaseProductInfo(id, name, price, quantity);

    }

}
