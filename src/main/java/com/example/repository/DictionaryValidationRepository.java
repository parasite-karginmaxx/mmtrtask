package com.example.repository;

import com.example.model.DictionaryValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DictionaryValidationRepository extends JpaRepository<DictionaryValidation, Long> {
    Optional<DictionaryValidation> findByDictionaryName(String dictionaryName);
    void deleteByDictionaryName(String dictionaryName);
}
