package com.ambar.ecommerce_backend.service;

import com.ambar.ecommerce_backend.model.Cart;
import com.ambar.ecommerce_backend.model.Product;
import com.ambar.ecommerce_backend.repository.CartRepository;
import com.ambar.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    public Cart addToCart(int productId){
        Product product=productRepository.findById((long) productId).orElseThrow();

        if(product.getStock()<=0){
            throw new RuntimeException("Out of stock");
        }
        //reduce stock
        product.setStock(product.getStock()-1);
        productRepository.save(product);

        //Add to cart
        Cart cart=new Cart();
        cart.setProduct(product);
        cart.setQuantity(1);
        return cartRepository.save(cart);
    }
    public void removeFromCart(Long cartId){
        Cart cart=cartRepository.findById(Math.toIntExact(cartId))
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        //restore stock
        Product product=cart.getProduct();
    product.setStock(product.getStock() + cart.getQuantity());
    productRepository.save(product);

    //remove cart item
        cartRepository.deleteById(Math.toIntExact(cartId));
}
public Cart increaseQuantity(Long cartId){
        Cart cart=cartRepository.findById(Math.toIntExact(cartId))
                .orElseThrow(()->new RuntimeException("Cart item not found"));
        //get product object links to cart item
        Product product=cart.getProduct();

        //check stock
    if(product.getStock()<=0){
        throw new RuntimeException("Out of stock");
    }
    //increase quantity
    cart.setQuantity(cart.getQuantity()+1);
    //decrease stock
    product.setStock(product.getStock()-1);
    productRepository.save(product);
    return cartRepository.save(cart);
}

public Cart decreaseQuantity(Long cartId){
        Cart cart=cartRepository.findById(Math.toIntExact(cartId))
                .orElseThrow(()->new RuntimeException("Cart Item not found"));

        Product product=cart.getProduct();

        //decrease quantity
    cart.setQuantity(cart.getQuantity()-1);
    //increase stock
    product.setStock(product.getStock()+1);
    productRepository.save(product);

// remove if quantity becomes 0
    if(cart.getQuantity()<=0){
        cartRepository.delete(cart);
        return null;
    }
    return cartRepository.save(cart);}
}