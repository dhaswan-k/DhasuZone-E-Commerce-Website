package com.dhasuzone.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dhasuzone.ecommerce.model.Product;
import com.dhasuzone.ecommerce.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    public List<Product> getFeaturedProducts() {
        return productRepository.findAll().stream()
                .filter(Product::isFeatured)
                .collect(Collectors.toList());
    }
    
    public List<Product> getProductsOnSale() {
        return productRepository.findAll().stream()
                .filter(Product::isOnSale)
                .collect(Collectors.toList());
    }
    
    public List<String> getAllCategories() {
        return productRepository.findAll().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
    
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    public List<Product> searchProducts(String keyword) {
        return productRepository.findAll().stream()
                .filter(product -> 
                    product.getName().toLowerCase().contains(keyword.toLowerCase()) || 
                    product.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
                    product.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());
    }
}