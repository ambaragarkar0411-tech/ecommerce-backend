package com.ambar.ecommerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private double price;
    private int quantity;
    private double totalPrice;
    private String paymentMethod;
    private String address;
    private String status;
    private String customerName;

    private String email;
}
