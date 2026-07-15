package com.cart.demo.controller;

import com.cart.demo.model.dto.purchase.PurchaseOrderResponse;
import com.cart.demo.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/purchase")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(purchaseOrderService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<PurchaseOrderResponse> purchaseCart(@PathVariable Long userId) {
        return new ResponseEntity<>(purchaseOrderService.purchase(userId), HttpStatus.CREATED);
    }

}
