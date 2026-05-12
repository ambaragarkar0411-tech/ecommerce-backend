package com.ambar.ecommerce_backend.controller;

import com.ambar.ecommerce_backend.model.Order;
import com.ambar.ecommerce_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public Order placeOrder(@RequestBody Order order){
        return service.placeOrder(order);
    }
}
