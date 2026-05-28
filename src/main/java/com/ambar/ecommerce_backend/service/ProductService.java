package com.ambar.ecommerce_backend.service;


import com.ambar.ecommerce_backend.dto.ProductDTO;
import com.ambar.ecommerce_backend.exception.ResourceNotFoundException;
import com.ambar.ecommerce_backend.model.Product;
import com.ambar.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

//    public List<Product> getAllProducts1(){
//        return repo.findAll();
//    }
//    public Product saveProduct1(Product product){
//        return repo.save(product);
//    }
//    public Product getProductById(Long id){
//        return repo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//    }

//    public Product updateProduct(Long id,Product updatedProduct){
//        Product product=repo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        product.setName(updatedProduct.getName());
//        product.setDescription(updatedProduct.getDescription());
//        product.setPrice(updatedProduct.getPrice());
//        product.setStock(updatedProduct.getStock());
//
//        return repo.save(product);
//    }
//    public void deleteProduct(Long id) {
//        repo.deleteById(id);
//    }
//convert dto to entity
    private Product convertToEntity(ProductDTO dto){
        Product product=new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }
//convert entity to dto
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setId(product.getId()); // ✅ VERY IMPORTANT
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }

    public ProductDTO saveProduct(ProductDTO dto) {
        Product product = convertToEntity(dto);
        Product saved = repo.save(product);
        return convertToDTO(saved);
    }
    public List<ProductDTO> getAllProducts() {
        return repo.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ProductDTO getProductById(Long id) {

        Product product = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return convertToDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductDTO dto) {

        Product product = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // update fields
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());

        Product updated = repo.save(product);

        return convertToDTO(updated);
    }

//    public void deleteProduct(Long id) {
//
//        Product product = repo.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
//
//        repo.delete(product);
//    }

    public void deleteProduct(Long id) throws IOException {

        Product product = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        // delete image file
        if (product.getImageUrl() != null) {

            Path imagePath = Paths.get(
                    "uploads/",
                    product.getImageUrl()
            );

            Files.deleteIfExists(imagePath);
        }

        // delete product from database
        repo.delete(product);
    }

//    With pagination:
//    Fetch limited data
//    Faster API
//    Scalable
//    Used in real apps (Amazon, Flipkart, etc.)
//    | Concept     | Purpose                  |
//            | ----------- | ------------------------ |
//            | PageRequest | Create pagination config |
//            | Pageable    | Holds page + size + sort |
//            | Page        | Paginated result         |
//            | map()       | Convert entity → DTO     |

    private Sort getSort(String sortBy) {
        if (sortBy.contains(",")) {
            String[] parts = sortBy.split(",");

            String field = parts[0];
            String direction = parts[1];

            return direction.equalsIgnoreCase("desc")
                    ? Sort.by(field).descending()
                    : Sort.by(field).ascending();
        }

        return Sort.by(sortBy).ascending(); // default
    }

//    public Page<ProductDTO> getProducts(int page, int size, String sortBy){
//        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));
//        Page<Product> productPage=repo.findAll(pageable);
//        return productPage.map(this::convertToDTO);
//    }

    public Page<ProductDTO> getProducts(int page, int size, String sortBy) {

        Sort sort = getSort(sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = repo.findAll(pageable);

        return productPage.map(this::convertToDTO);
    }

//    Page<ProductDTO> means we are returning paginated data, not a full list — it includes content + metadata like total pages, total elements, etc.
//    We use ProductDTO (instead of Product) to expose only required data to client and hide internal entity details.
//    So together, it means: “return paginated, safe (DTO-based) response to the client.”

//    public Page<ProductDTO> searchProducts(String keyword,int page,int size,String sortBy){
//        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));
//        Page<Product> productPage=repo.findByNameContainingIgnoreCase(keyword,pageable);
//        return productPage.map(this::convertToDTO);
//    }

    public Page<ProductDTO> searchProducts(String keyword, int page, int size, String sortBy) {

        Sort sort = getSort(sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = repo.findByNameContainingIgnoreCase(keyword, pageable);

        return productPage.map(this::convertToDTO);
    }

//    public Page<ProductDTO> filterProducts(double minPrice,double maxPrice,int stock,int page,int size,String sortBy){
//        Pageable pageable=PageRequest.of(page,size,Sort.by(sortBy));
//        Page<Product> productPage=repo.filterProducts(minPrice,maxPrice,stock,pageable);
//        return productPage.map(this::convertToDTO);
//    }

    public Page<ProductDTO> filterProducts(double minPrice, double maxPrice, int stock, int page, int size, String sortBy) {

        Sort sort = getSort(sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = repo.filterProducts(minPrice, maxPrice, stock, pageable);

        return productPage.map(this::convertToDTO);
    }
//    public List<Product> getProductsByCategory(String category){
//        return repo.findByCategory(category);


    public List<ProductDTO> getProductsByCategory(String category){
        return repo.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    public Product updateProduct(

            Long id,
            String name,
            double price,
            int stock,
            String category,
            MultipartFile image

    ) throws IOException {

        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);

        // image update optional
        if (image != null && !image.isEmpty()) {

            String uploadDir = "uploads/";

            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + image.getOriginalFilename();

            Path filePath =
                    Paths.get(uploadDir, fileName);

            Files.write(filePath, image.getBytes());

            product.setImageUrl(fileName);
        }

        return repo.save(product);
    }
}
