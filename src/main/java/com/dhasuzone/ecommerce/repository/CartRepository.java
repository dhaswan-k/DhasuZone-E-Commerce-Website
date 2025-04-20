package com.dhasuzone.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.dhasuzone.ecommerce.model.Cart;

public interface CartRepository {
    List<Cart> findAll();
    Optional<Cart> findById(Long id);
    Optional<Cart> findByUserId(Long userId);
    Cart save(Cart cart);
    void deleteById(Long id);
}