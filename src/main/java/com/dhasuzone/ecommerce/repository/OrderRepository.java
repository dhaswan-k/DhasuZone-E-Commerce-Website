package com.dhasuzone.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.dhasuzone.ecommerce.model.Order;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order save(Order order);
    void deleteById(Long id);
}