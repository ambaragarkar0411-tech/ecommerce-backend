package com.ambar.ecommerce_backend.model;

import jakarta.persistence.*;
import com.ambar.ecommerce_backend.model.Product;
import lombok.Data;

@Data
@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name="product_id") //fk column in db
    private Product product;
}
