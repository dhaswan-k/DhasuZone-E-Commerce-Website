package com.dhasuzone.ecommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhasuzone.ecommerce.model.Product;
import com.dhasuzone.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {
    
    private final ProductService productService;
    
    public ChatbotController(ProductService productService) {
        this.productService = productService;
    }
    
    @PostMapping("/query")
    public ResponseEntity<Map<String, Object>> processQuery(@RequestBody Map<String, String> request) {
        String query = request.get("query").toLowerCase();
        Map<String, Object> response = new HashMap<>();
        
        if (query.contains("product") && (query.contains("category") || query.contains("categories"))) {
            List<String> categories = productService.getAllCategories();
            response.put("type", "categories");
            response.put("message", "We have products in the following categories:");
            response.put("data", categories);
        } else if (query.contains("featured") || query.contains("best")) {
            List<Product> featuredProducts = productService.getFeaturedProducts();
            response.put("type", "products");
            response.put("message", "Here are our featured products:");
            response.put("data", featuredProducts);
        } else if (query.contains("sale") || query.contains("discount") || query.contains("offer")) {
            List<Product> saleProducts = productService.getProductsOnSale();
            response.put("type", "products");
            response.put("message", "Check out these great deals:");
            response.put("data", saleProducts);
        } else if (query.contains("contact") || query.contains("help") || query.contains("support")) {
            response.put("type", "text");
            response.put("message", "You can contact our support team at support@dhasuzone.com or call us at 1800-123-4567.");
        } else if (query.contains("order") && (query.contains("track") || query.contains("status"))) {
            response.put("type", "link");
            response.put("message", "You can track your order status here:");
            response.put("url", "/orders");
        } else if (query.contains("payment") || query.contains("pay")) {
            response.put("type", "text");
            response.put("message", "We accept all major credit/debit cards, net banking, UPI, and wallet payments through Razorpay.");
        } else if (query.contains("shipping") || query.contains("delivery")) {
            response.put("type", "text");
            response.put("message", "We offer free shipping on orders above ₹499. Standard delivery takes 3-5 business days.");
        } else if (query.contains("return") || query.contains("refund")) {
            response.put("type", "text");
            response.put("message", "We have a 30-day return policy. You can initiate a return from your order details page.");
        } else {
            // Search for products matching the query
            List<Product> searchResults = productService.searchProducts(query);
            
            if (!searchResults.isEmpty()) {
                response.put("type", "products");
                response.put("message", "Here are some products you might be interested in:");
                response.put("data", searchResults);
            } else {
                response.put("type", "text");
                response.put("message", "I'm sorry, I couldn't understand your query. Please try asking about our products, categories, orders, or contact information.");
            }
        }
        
        return ResponseEntity.ok(response);
    }
}