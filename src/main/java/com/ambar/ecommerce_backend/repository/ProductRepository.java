package com.ambar.ecommerce_backend.repository;

import com.ambar.ecommerce_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}