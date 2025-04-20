package com.dhasuzone.ecommerce.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dhasuzone.ecommerce.model.Order;
import com.dhasuzone.ecommerce.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
    
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping
    public String viewOrders(Model model) {
        // For demo purposes, using user ID 1. In real app, get from authenticated user
        Long userId = 1L;
        model.addAttribute("orders", orderService.getOrdersByUserId(userId));
        return "orders/list";
    }
    
    @GetMapping("/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "orders/detail";
        }
        return "redirect:/orders";
    }
    
    @GetMapping("/{id}/confirmation")
    public String orderConfirmation(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "orders/confirmation";
        }
        return "redirect:/orders";
    }
}