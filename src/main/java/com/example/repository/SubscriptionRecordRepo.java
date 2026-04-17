package com.example.repository;

import com.example.model.SubscriptionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubscriptionRecordRepo extends JpaRepository<SubscriptionRecord, Long> {
    Optional<SubscriptionRecord> findBySubscriptionId(String subscriptionId);
}
