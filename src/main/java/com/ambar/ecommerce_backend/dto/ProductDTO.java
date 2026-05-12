package com.ambar.ecommerce_backend.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    @NotBlank(message="Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @Positive(message="Price must be greater than 0")
    private double price;
    @Min(value=0,message="Stock cannot be negative")
    private int stock;

    // getters & setters
}
