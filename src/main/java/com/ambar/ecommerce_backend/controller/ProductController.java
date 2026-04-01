package com.ambar.ecommerce_backend.controller;


import com.ambar.ecommerce_backend.model.Product;
import com.ambar.ecommerce_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getAll(){
        return service.getAllProducts();
    }

    @PostMapping
    public Product create(@RequestBody Product product){
        return service.saveProduct(product);
    }
}
