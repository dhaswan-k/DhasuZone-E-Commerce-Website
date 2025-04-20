package com.dhasuzone.ecommerce.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import com.dhasuzone.ecommerce.model.Order;

import jakarta.annotation.PostConstruct;

@Repository
public class JsonOrderRepository implements OrderRepository {
    
    private final String DATA_FILE = "src/main/resources/data/orders.json";
    private List<Order> orders = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @PostConstruct
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        
        try {
            Path path = Paths.get(DATA_FILE);
            File file = path.toFile();
            
            if (file.exists()) {
                orders = objectMapper.readValue(file, new TypeReference<List<Order>>() {});
            } else {
                // Create directory if it doesn't exist
                Files.createDirectories(path.getParent());
                
                // Initialize with empty list
                orders = new ArrayList<>();
                saveToFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Initialize with empty list if file read fails
            orders = new ArrayList<>();
        }
    }
    
    private void saveToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(DATA_FILE), orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Order> findAll() {
        return orders;
    }
    
    @Override
    public Optional<Order> findById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            // New order
            Long newId = orders.stream()
                    .mapToLong(Order::getId)
                    .max()
                    .orElse(0) + 1;
            order.setId(newId);
            orders.add(order);
        } else {
            // Update existing order
            orders = orders.stream()
                    .map(o -> o.getId().equals(order.getId()) ? order : o)
                    .collect(Collectors.toList());
        }
        
        saveToFile();
        return order;
    }
    
    @Override
    public void deleteById(Long id) {
        orders = orders.stream()
                .filter(order -> !order.getId().equals(id))
                .collect(Collectors.toList());
        
        saveToFile();
    }
}