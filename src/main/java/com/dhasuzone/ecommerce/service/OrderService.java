package com.dhasuzone.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dhasuzone.ecommerce.model.Cart;
import com.dhasuzone.ecommerce.model.CartItem;
import com.dhasuzone.ecommerce.model.Order;
import com.dhasuzone.ecommerce.model.OrderItem;
import com.dhasuzone.ecommerce.repository.OrderRepository;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final CartService cartService;
    
    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public Order createOrderFromCart(Long userId, String transactionId) {
        Cart cart = cartService.getCartByUserId(userId);
        
        Order order = new Order();
        order.setUserId(userId);
        order.setTotal(cart.getTotal());
        order.setStatus("PAID");
        order.setOrderDate(LocalDateTime.now());
        order.setTransactionId(transactionId);
        
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProduct().getName());
            orderItem.setProductImage(cartItem.getProduct().getImageUrl());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        }
        
        order.setItems(orderItems);
        
        return orderRepository.save(order);
    }
    
    public Order updateOrderStatus(Long orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }
}