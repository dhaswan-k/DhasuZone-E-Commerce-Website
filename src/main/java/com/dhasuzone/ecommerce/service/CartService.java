package com.dhasuzone.ecommerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dhasuzone.ecommerce.model.Cart;
import com.dhasuzone.ecommerce.model.Product;
import com.dhasuzone.ecommerce.model.User;
import com.dhasuzone.ecommerce.repository.CartRepository;
import com.dhasuzone.ecommerce.repository.ProductRepository;
import com.dhasuzone.ecommerce.repository.UserRepository;

@Service
public class CartService {
    
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    
    public Cart getCartByUserId(Long userId) {
        Optional<Cart> existingCart = cartRepository.findByUserId(userId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        } else {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        }
    }
    
    public Cart addToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Optional<Product> productOpt = productRepository.findById(productId);
        
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            cart.addItem(product, quantity);
            return cartRepository.save(cart);
        }
        
        return cart;
    }
    
    public Cart removeFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        cart.removeItem(productId);
        return cartRepository.save(cart);
    }
    
    public Cart updateCartItemQuantity(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        cart.updateItemQuantity(productId, quantity);
        return cartRepository.save(cart);
    }
    
    public Cart clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().clear();
        cart.setTotal(java.math.BigDecimal.ZERO);
        return cartRepository.save(cart);
    }
}