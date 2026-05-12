package com.ambar.ecommerce_backend.repository;

import com.ambar.ecommerce_backend.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    boolean existsByProductId(Long productId);
}
