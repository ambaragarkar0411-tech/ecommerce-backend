package com.ambar.ecommerce_backend.repository;

import com.ambar.ecommerce_backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByPriceBetweenAndStockGreaterThan(
            double minPrice,
            double maxPrice,
            int stock,
            Pageable pageable
    );
}