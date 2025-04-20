package com.dhasuzone.ecommerce.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long id;
    private Long productId;
    private Long userId;
    private String username;
    private int rating;
    private String comment;
    private LocalDateTime dateCreated;
}