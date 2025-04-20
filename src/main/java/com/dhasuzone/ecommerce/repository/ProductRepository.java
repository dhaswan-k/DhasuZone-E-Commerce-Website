package com.dhasuzone.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.dhasuzone.ecommerce.model.Product;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
}