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
public class Cart {
    private Long id;
    private Long userId;
    private List<CartItem> items = new ArrayList<>();
    private BigDecimal total;
    
    public void addItem(Product product, int quantity) {
        CartItem existingItem = findCartItem(product.getId());
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setProductId(product.getId());
            item.setProduct(product);
            item.setQuantity(quantity);
            items.add(item);
        }
        calculateTotal();
    }
    
    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
        calculateTotal();
    }
    
    public void updateItemQuantity(Long productId, int quantity) {
        CartItem item = findCartItem(productId);
        if (item != null) {
            item.setQuantity(quantity);
            calculateTotal();
        }
    }
    
    private CartItem findCartItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }
    
    private void calculateTotal() {
        total = items.stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}