package com.ambar.ecommerce_backend.controller;

import com.ambar.ecommerce_backend.model.Cart;
import com.ambar.ecommerce_backend.repository.CartRepository;
import com.ambar.ecommerce_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/add/{productId}")
    public Cart addToCart(@PathVariable int productId) {
        return cartService.addToCart(productId);
    }

    @GetMapping
    public List<Cart>getCartItems(){
        return cartRepository.findAll();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String>removeFromCart(@PathVariable Long cartId){
        cartService.removeFromCart(cartId);
        return ResponseEntity.ok("Item removed from cart");
    }

    @PutMapping("/increase/{cartId}")
    public Cart increaseQuantity(@PathVariable Long cartId){
        return cartService.increaseQuantity(cartId);
    }
    @PutMapping("/decrease/{cartId}")
    public Cart decreaseQuantity(@PathVariable Long cartId){
        return cartService.decreaseQuantity(cartId);
    }
    @GetMapping("/count")
    public int getCartCount() {

        List<Cart> carts = cartRepository.findAll();

        return carts.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
    }
}
