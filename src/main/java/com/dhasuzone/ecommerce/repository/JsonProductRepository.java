package com.dhasuzone.ecommerce.repository;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import com.dhasuzone.ecommerce.model.Product;

import jakarta.annotation.PostConstruct;

@Repository
public class JsonProductRepository implements ProductRepository {
    
    private final String DATA_FILE = "src/main/resources/data/products.json";
    private List<Product> products = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(DATA_FILE);
            File file = path.toFile();
            
            if (file.exists()) {
                products = objectMapper.readValue(file, new TypeReference<List<Product>>() {});
            } else {
                // Create directory if it doesn't exist
                Files.createDirectories(path.getParent());
                
                // Initialize with sample data
                initSampleData();
                saveToFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Initialize with sample data if file read fails
            initSampleData();
        }
    }
    
    private void initSampleData() {
        products = new ArrayList<>();
        
        // Sample products for different categories
        // Smartphones
        Product phone1 = new Product();
        phone1.setId(1L);
        phone1.setName("DhasuPhone Pro Max");
        phone1.setDescription("Latest flagship smartphone with 6.7 inch display, 108MP camera and 5G connectivity.");
        phone1.setPrice(new BigDecimal("49999.00"));
        phone1.setStock(50);
        phone1.setImageUrl("https://plus.unsplash.com/premium_photo-1681336999500-e4fc553a618a?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3");
        phone1.setCategory("Smartphones");
        phone1.setTags(List.of("smartphone", "5G", "high-end"));
        phone1.setRating(4.8);
        phone1.setFeatured(true);
        phone1.setOnSale(false);
        phone1.setBrand("DhasuTech");
        phone1.setSpecifications(List.of("6.7 inch AMOLED display", "108MP camera", "5G", "5000mAh battery", "12GB RAM", "256GB storage"));
        
        // Laptops
        Product laptop1 = new Product();
        laptop1.setId(2L);
        laptop1.setName("DhasuBook Ultra");
        laptop1.setDescription("Powerful laptop with 16GB RAM, 512GB SSD and dedicated graphics.");
        laptop1.setPrice(new BigDecimal("79999.00"));
        laptop1.setStock(30);
        laptop1.setImageUrl("https://images.unsplash.com/photo-1602080858428-57174f9431cf?q=80&w=1951&auto=format&fit=crop&ixlib=rb-4.0.3");
        laptop1.setCategory("Laptops");
        laptop1.setTags(List.of("laptop", "performance", "gaming"));
        laptop1.setRating(4.7);
        laptop1.setFeatured(true);
        laptop1.setOnSale(true);
        laptop1.setSalePrice(new BigDecimal("69999.00"));
        laptop1.setBrand("DhasuTech");
        laptop1.setSpecifications(List.of("15.6 inch 4K display", "Intel Core i7", "16GB RAM", "512GB SSD", "RTX 3070", "Backlit keyboard"));
        
        // Clothing
        Product tshirt1 = new Product();
        tshirt1.setId(3L);
        tshirt1.setName("Premium Cotton T-Shirt");
        tshirt1.setDescription("Comfortable 100% cotton t-shirt with stylish design.");
        tshirt1.setPrice(new BigDecimal("999.00"));
        tshirt1.setStock(100);
        tshirt1.setImageUrl("https://images.unsplash.com/photo-1581655353564-df123a1eb820?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3");
        tshirt1.setCategory("Clothing");
        tshirt1.setTags(List.of("tshirt", "clothing", "fashion"));
        tshirt1.setRating(4.5);
        tshirt1.setFeatured(false);
        tshirt1.setOnSale(true);
        tshirt1.setSalePrice(new BigDecimal("799.00"));
        tshirt1.setBrand("DhasuStyle");
        tshirt1.setSpecifications(List.of("100% cotton", "Machine washable", "Available in multiple colors", "Size: S, M, L, XL"));
        
        // Home Appliances
        Product tv1 = new Product();
        tv1.setId(4L);
        tv1.setName("DhasuView 55-inch 4K Smart TV");
        tv1.setDescription("55-inch 4K Ultra HD Smart LED TV with built-in streaming apps.");
        tv1.setPrice(new BigDecimal("39999.00"));
        tv1.setStock(20);
        tv1.setImageUrl("https://images.unsplash.com/photo-1593784991095-a205069470b6?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3");
        tv1.setCategory("Electronics");
        tv1.setTags(List.of("tv", "4k", "smart tv"));
        tv1.setRating(4.6);
        tv1.setFeatured(true);
        tv1.setOnSale(false);
        tv1.setBrand("DhasuTech");
        tv1.setSpecifications(List.of("55-inch 4K UHD", "HDR10+", "Dolby Vision", "Smart TV with Netflix, Prime Video", "3 HDMI ports", "2 USB ports"));
        
        // Books
        Product book1 = new Product();
        book1.setId(5L);
        book1.setName("The Dhasu Way: Path to Success");
        book1.setDescription("Bestselling self-help book for personal and professional growth.");
        book1.setPrice(new BigDecimal("499.00"));
        book1.setStock(200);
        book1.setImageUrl("https://images.unsplash.com/photo-1544947950-fa07a98d237f?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3");
        book1.setCategory("Books");
        book1.setTags(List.of("book", "self-help", "bestseller"));
        book1.setRating(4.9);
        book1.setFeatured(false);
        book1.setOnSale(true);
        book1.setSalePrice(new BigDecimal("399.00"));
        book1.setBrand("DhasuPublishers");
        book1.setSpecifications(List.of("Hardcover", "300 pages", "International bestseller", "Available in multiple languages"));
        
        products.add(phone1);
        products.add(laptop1);
        products.add(tshirt1);
        products.add(tv1);
        products.add(book1);
        
        // Add more sample products for each category
        for (int i = 6; i <= 20; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setName("Product " + i);
            product.setDescription("This is a sample product description for product " + i);
            product.setPrice(new BigDecimal(String.valueOf(1000 + (i * 100))));
            product.setStock(30 + i);
            product.setImageUrl("https://source.unsplash.com/random/300x200?product=" + i);
            
            // Assign to different categories
            if (i % 5 == 0) {
                product.setCategory("Electronics");
            } else if (i % 5 == 1) {
                product.setCategory("Clothing");
            } else if (i % 5 == 2) {
                product.setCategory("Books");
            } else if (i % 5 == 3) {
                product.setCategory("Home & Kitchen");
            } else {
                product.setCategory("Sports & Outdoors");
            }
            
            product.setTags(List.of("sample", "product" + i));
            product.setRating(4.0 + (Math.random() * 1.0));
            product.setFeatured(i % 7 == 0);
            product.setOnSale(i % 4 == 0);
            
            if (product.isOnSale()) {
                product.setSalePrice(product.getPrice().multiply(new BigDecimal("0.85")));
            }
            
            product.setBrand("DhasuBrand");
            product.setSpecifications(List.of("Specification 1", "Specification 2", "Specification 3"));
            
            products.add(product);
        }
    }
    
    private void saveToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(DATA_FILE), products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Product> findAll() {
        return products;
    }
    
    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            // New product
            Long newId = products.stream()
                    .mapToLong(Product::getId)
                    .max()
                    .orElse(0) + 1;
            product.setId(newId);
            products.add(product);
        } else {
            // Update existing product
            products = products.stream()
                    .map(p -> p.getId().equals(product.getId()) ? product : p)
                    .collect(Collectors.toList());
        }
        
        saveToFile();
        return product;
    }
    
    @Override
    public void deleteById(Long id) {
        products = products.stream()
                .filter(product -> !product.getId().equals(id))
                .collect(Collectors.toList());
        
        saveToFile();
    }
}