package com.ambar.ecommerce_backend.controller;

import com.ambar.ecommerce_backend.model.Wishlist;
import com.ambar.ecommerce_backend.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin
public class WishlistController {
    @Autowired
    private WishlistService service;

    @PostMapping("/add/{productId}")
    public Wishlist addToWishlist(@PathVariable Long productId){
        return service.addToWishlist(productId);
    }

    @GetMapping
    public List<Wishlist> getWishlist(){
        return service.getWishlist();
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<String> removeWishlist(@PathVariable Long wishlistId){
        service.removeWishlist(wishlistId);
        return ResponseEntity.ok("Removed from Wishlist");
    }
}
