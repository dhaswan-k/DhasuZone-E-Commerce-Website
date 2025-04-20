package com.dhasuzone.ecommerce.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dhasuzone.ecommerce.model.Cart;
import com.dhasuzone.ecommerce.service.CartService;
import com.dhasuzone.ecommerce.service.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;
    
    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;
    
    private final CartService cartService;
    private final OrderService orderService;
    
    public PaymentController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }
    
    @GetMapping("/process")
    public String processPayment(Model model) {
        try {
            // For demo purposes, using user ID 1. In real app, get from authenticated user
            Long userId = 1L;
            Cart cart = cartService.getCartByUserId(userId);
            
            if (cart.getItems().isEmpty()) {
                return "redirect:/cart";
            }
            
            // Convert to paisa/cents (as Razorpay expects amount in smallest currency unit)
            int amountInPaisa = cart.getTotal().multiply(new java.math.BigDecimal(100)).intValue();
            
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaisa);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + System.currentTimeMillis());
            
            Order razorpayOrder = razorpay.orders.create(orderRequest);
            
            model.addAttribute("order", razorpayOrder);
            model.addAttribute("razorpayKeyId", razorpayKeyId);
            model.addAttribute("cart", cart);
            
            return "payment/razorpay";
            
        } catch (RazorpayException e) {
            e.printStackTrace();
            model.addAttribute("error", "Payment gateway error. Please try again later.");
            return "payment/error";
        }
    }
    
    @PostMapping("/complete")
    public String completePayment(@RequestParam String razorpay_payment_id, 
                                 @RequestParam String razorpay_order_id,
                                 @RequestParam String razorpay_signature) {
        // For demo purposes, using user ID 1. In real app, get from authenticated user
        Long userId = 1L;
        
        // Verify signature (in production you would validate the signature here)
        
        // Create order in our system
        com.dhasuzone.ecommerce.model.Order order = orderService.createOrderFromCart(userId, razorpay_payment_id);
        
        // Clear the cart after successful order
        cartService.clearCart(userId);
        
        return "redirect:/orders/" + order.getId() + "/confirmation";
    }
}