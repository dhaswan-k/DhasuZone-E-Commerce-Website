package com.dhasuzone.ecommerce.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private String imageUrl;
    private String category;
    private List<String> tags = new ArrayList<>();
    private double rating;
    private List<Review> reviews = new ArrayList<>();
    private boolean featured;
    private boolean onSale;
    private BigDecimal salePrice;
    private String brand;
    private List<String> specifications = new ArrayList<>();
}