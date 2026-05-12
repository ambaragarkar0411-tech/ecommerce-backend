package com.ambar.ecommerce_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Wishlist {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

}
