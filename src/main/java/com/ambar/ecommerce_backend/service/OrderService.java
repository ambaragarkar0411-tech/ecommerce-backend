package com.ambar.ecommerce_backend.service;

import com.ambar.ecommerce_backend.model.Order;
import com.ambar.ecommerce_backend.repository.OrderRepository;
import com.ambar.ecommerce_backend.utility.ReceiptGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ReceiptGenerator receiptGenerator;

    @Autowired
    private EmailService emailService;

    public Order placeOrder(Order order){

        // Set status BEFORE save
        order.setStatus("Order Confirmed");

        // Save order
        Order savedOrder =
                repository.save(order);

        // Email body
        String emailBody =
                "Hello "
                        + savedOrder.getCustomerName()
                        + "\n\n"
                        + "Your order has been placed successfully.\n"
                        + "Order ID: "
                        + savedOrder.getId()
                        + "\n"
                        + "Total Amount: ₹"
                        + savedOrder.getTotalPrice()
                        + "\n\n"
                        + "Thank you for shopping with us.";

        // Generate PDF
        ByteArrayInputStream pdfStream =
                receiptGenerator.generateReceipt(savedOrder);

        // Send Email + PDF
        emailService.sendOrderReceipt(
                savedOrder.getEmail(),
                pdfStream,
                "Order Confirmation",
                emailBody
        );

        return savedOrder;
    }
}