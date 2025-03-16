package com.example.repository;

import com.example.model.KeyPopularity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyPopularityRepository extends JpaRepository<KeyPopularity, Long> {
    Optional<KeyPopularity> findByKeyAndDictionaryName(String key, String dictionaryName);
    Optional<KeyPopularity> findByKey(String key);
}
