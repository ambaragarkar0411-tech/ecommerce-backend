package com.ambar.ecommerce_backend.controller;


import com.ambar.ecommerce_backend.dto.ProductDTO;
import com.ambar.ecommerce_backend.model.Product;
import com.ambar.ecommerce_backend.repository.ProductRepository;
import com.ambar.ecommerce_backend.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5179")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository productRepository;

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

//    @PostMapping
//    public ProductDTO create(@Valid @RequestBody ProductDTO dto) {
//        return service.saveProduct(dto);
//    }

    @PostMapping
    public Product create(

            @RequestParam String name,
            @RequestParam double price,
            @RequestParam int stock,
            @RequestParam String category,

            @RequestParam("image") MultipartFile image

    ) throws IOException {

        String fileName =
                System.currentTimeMillis()
                        + "_"
                        + image.getOriginalFilename();

        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(
                image.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING
        );

        Product product = new Product();

        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);

        product.setImageUrl(fileName);

        return productRepository.save(product);
    }

//    @PostMapping
//    public Product addProduct(
//
//            @RequestParam String name,
//            @RequestParam double price,
//            @RequestParam int stock,
//            @RequestParam String category,
//            @RequestParam("image") MultipartFile image
//
//    ) throws IOException {
//
//        String fileName =
//                System.currentTimeMillis()
//                        + "_"
//                        + image.getOriginalFilename();
//
//        Path uploadPath = Paths.get("uploads");
//
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        Files.copy(
//                image.getInputStream(),
//                ((java.nio.file.Path) uploadPath).resolve(fileName),
//                StandardCopyOption.REPLACE_EXISTING
//        );
//
//        Product product = new Product();
//
//        product.setName(name);
//        product.setPrice(price);
//        product.setStock(stock);
//        product.setCategory(category);
//
//        product.setImageUrl(fileName);
//
//        return productRepository.save(product);
//    }
//    @GetMapping
//    public List<ProductDTO> getAll() {
//        return service.getAllProducts();
//    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

//    @PutMapping("/{id}")
//    public ProductDTO update(@PathVariable Long id,@RequestBody ProductDTO dto){
//        return service.updateProduct(id,dto);
//    }

    @PutMapping("/{id}")
    public Product updateProduct(

            @PathVariable Long id,

            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam("category") String category,

            @RequestParam(value = "image", required = false)
            MultipartFile image

    ) throws IOException {

        return service.updateProduct(
                id,
                name,
                price,
                stock,
                category,
                image
        );
    }
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable Long id){
//        service.deleteProduct(id);
//        return "Product deleted successfully";
//    }
        @DeleteMapping("/{id}")
        public String delete(@PathVariable Long id)
                throws IOException {

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

    @GetMapping("/category/{category}")
    public List<ProductDTO> getProductsByCategory(@PathVariable String category){
        return service.getProductsByCategory(category);
    }
}
