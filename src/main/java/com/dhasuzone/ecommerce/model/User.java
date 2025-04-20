package com.dhasuzone.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String address;
    private String phoneNumber;
    private List<Order> orders = new ArrayList<>();
    private Cart cart;
    private List<Product> wishlist = new ArrayList<>();
    private String role; // ROLE_USER, ROLE_ADMIN
    private boolean active;
}