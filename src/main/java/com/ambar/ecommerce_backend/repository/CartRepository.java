package com.ambar.ecommerce_backend.repository;

import com.ambar.ecommerce_backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
}
