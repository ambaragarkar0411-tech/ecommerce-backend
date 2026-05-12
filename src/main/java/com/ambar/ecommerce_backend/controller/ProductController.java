package com.ambar.ecommerce_backend.controller;


import com.ambar.ecommerce_backend.dto.ProductDTO;
import com.ambar.ecommerce_backend.model.Product;
import com.ambar.ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5179")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService service;

//    @GetMapping
//    public List<Product> getAll1(){
//        return service.getAllProducts1();
//    }

//    @PostMapping
//    public Product create1(@RequestBody Product product){
//        return service.saveProduct1(product);
//    }
//    @GetMapping("/{id}")
//    public Product getById(@PathVariable Long id){
//        return service.getProductById(id);
//    }
//    @PutMapping("/{id}")
//    public Product update(@PathVariable Long id,@RequestBody Product product){
//        return service.updateProduct(id, product);
//    }
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable Long id){
//        service.deleteProduct(id);
//        return "Product deleted successfully";
//    }

    @PostMapping
    public ProductDTO create(@Valid @RequestBody ProductDTO dto) {
        return service.saveProduct(dto);
    }

//    @GetMapping
//    public List<ProductDTO> getAll() {
//        return service.getAllProducts();
//    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id,@RequestBody ProductDTO dto){
        return service.updateProduct(id,dto);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.deleteProduct(id);
        return "Product deleted successfully";
    }
    @GetMapping
    public Page<ProductDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "8") int size,
                                   @RequestParam(defaultValue = "id") String sortBy)
    {
        return service.getProducts(page,size,sortBy);
    }

    @GetMapping("/search")
    public Page<ProductDTO> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return service.searchProducts(keyword, page, size, sortBy);
    }

    @GetMapping("/filter")
    public Page<ProductDTO> filter(
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            @RequestParam(defaultValue = "0") int stock,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "price") String sortBy) {

        return service.filterProducts(minPrice, maxPrice, stock, page, size, sortBy);
    }
}
