package com.example.repository;

import com.example.model.CallbackSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CallbackSubscriptionRepository extends JpaRepository<CallbackSubscription, Long> {
    Optional<CallbackSubscription> findById(Long id);
    List<CallbackSubscription> findByDictionaryId(String dictionaryId);
}
