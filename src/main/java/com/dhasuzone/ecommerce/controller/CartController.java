package com.dhasuzone.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dhasuzone.ecommerce.model.Cart;
import com.dhasuzone.ecommerce.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    private final CartService cartService;
    
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @GetMapping
    public String viewCart(Model model) {
        // For demo purposes, using user ID 1. In real app, get from authenticated user
        Long userId = 1L;
        Cart cart = cartService.getCartByUserId(userId);
        model.addAttribute("cart", cart);
        return "cart/view";
    }
    
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, @RequestParam(defaultValue = "1") int quantity) {
        // For demo purposes, using user ID 1. In real app, get from authenticated user
        Long userId = 1L;
        cartService.addToCart(userId, productId, quantity);
        return "redirect:/products/" + productId + "?added=true";
    }
    
    @PostMapping("/update/{productId}")
    public String updateCartItem(@PathVariable Long productId, @RequestParam int quantity) {
        // For demo purposes, using user ID 1. In real app, get from authenticated user
        Long userId = 1L;
        cartService.updateCartItemQuantity(userId, productId, quantity);
        return "redirect:/cart";
    }
    
    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        // For demo purposes, using user ID 1. In real app, get from authenticated user
        Long userId = 1L;
        cartService.removeFromCart(userId, productId);
        return "redirect:/cart";
    }
    
    @PostMapping("/clear")
    public String clearCart() {
        // For demo purposes, using user ID 1. In real app, get from authenticated user
        Long userId = 1L;
        cartService.clearCart(userId);
        return "redirect:/cart";
    }
    
    @GetMapping("/checkout")
    public String checkout(Model model) {
        // For demo purposes, using user ID 1. In real app, get from authenticated user
        Long userId = 1L;
        Cart cart = cartService.getCartByUserId(userId);
        model.addAttribute("cart", cart);
        return "cart/checkout";
    }
}