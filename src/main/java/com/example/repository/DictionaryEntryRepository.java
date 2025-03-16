package com.example.repository;

import com.example.model.DictionaryEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DictionaryEntryRepository extends JpaRepository<DictionaryEntry, Long> {
    Page<DictionaryEntry> findByDictionaryName(String dictionaryName, Pageable pageable);
    Page<DictionaryEntry> findByDictionaryNameAndKey(String dictionaryName, String key, Pageable pageable);
    Page<DictionaryEntry> findByDictionaryNameAndValuesContaining(String dictionaryName, String value, Pageable pageable);
    Page<DictionaryEntry> findByKey(String key, Pageable pageable);
    Page<DictionaryEntry> findByValuesContaining(String value, Pageable pageable);
    void deleteByKeyAndDictionaryName(String key, String dictionaryName);
    Optional<DictionaryEntry> findByKeyAndDictionaryName(String key, String dictionaryName);
}

