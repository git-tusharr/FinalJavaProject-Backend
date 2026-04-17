package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SubscriptionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String subscriptionId;
    private String status; // ACTIVE / PENDING / HALTED / COMPLETED / CANCELLED
    private LocalDateTime lastPaidAt;
}
