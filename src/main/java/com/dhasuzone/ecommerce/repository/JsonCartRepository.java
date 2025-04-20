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

import com.dhasuzone.ecommerce.model.Cart;

import jakarta.annotation.PostConstruct;

@Repository
public class JsonCartRepository implements CartRepository {
    
    private final String DATA_FILE = "src/main/resources/data/carts.json";
    private List<Cart> carts = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(DATA_FILE);
            File file = path.toFile();
            
            if (file.exists()) {
                carts = objectMapper.readValue(file, new TypeReference<List<Cart>>() {});
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
        carts = new ArrayList<>();
        
        // Sample cart for demo user
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUserId(1L);
        cart.setItems(new ArrayList<>());
        cart.setTotal(BigDecimal.ZERO);
        
        carts.add(cart);
    }
    
    private void saveToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(DATA_FILE), carts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Cart> findAll() {
        return carts;
    }
    
    @Override
    public Optional<Cart> findById(Long id) {
        return carts.stream()
                .filter(cart -> cart.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return carts.stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst();
    }
    
    @Override
    public Cart save(Cart cart) {
        if (cart.getId() == null) {
            // New cart
            Long newId = carts.stream()
                    .mapToLong(Cart::getId)
                    .max()
                    .orElse(0) + 1;
            cart.setId(newId);
            carts.add(cart);
        } else {
            // Update existing cart
            carts = carts.stream()
                    .map(c -> c.getId().equals(cart.getId()) ? cart : c)
                    .collect(Collectors.toList());
        }
        
        saveToFile();
        return cart;
    }
    
    @Override
    public void deleteById(Long id) {
        carts = carts.stream()
                .filter(cart -> !cart.getId().equals(id))
                .collect(Collectors.toList());
        
        saveToFile();
    }
}