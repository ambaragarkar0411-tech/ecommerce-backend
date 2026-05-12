package com.ambar.ecommerce_backend.service;

import com.ambar.ecommerce_backend.model.Product;
import com.ambar.ecommerce_backend.model.Wishlist;
import com.ambar.ecommerce_backend.repository.ProductRepository;
import com.ambar.ecommerce_backend.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ProductRepository productRepository;


    public Wishlist addToWishlist(Long productId){
        boolean exists =
                wishlistRepository.existsByProductId(productId);
        if (exists) {
            throw new RuntimeException(
                    "Product already in wishlist");
        }
        Product product=productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));
        Wishlist wishlist=new Wishlist();
        wishlist.setProduct(product);
        return wishlistRepository.save(wishlist);
    }
    public List<Wishlist>getWishlist(){
        return wishlistRepository.findAll();
    }

    public void removeWishlist(Long wishlistId){
         wishlistRepository.deleteById(wishlistId);
    }
}
