package com.example.controller;

import com.example.model.DictionaryValidation;
import com.example.repository.DictionaryValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dictionary")
public class AdminDictionaryController {
    @Autowired
    private DictionaryValidationRepository validationRepository;

    @PostMapping("/add")
    public ResponseEntity<Void> addDictionary(@RequestBody DictionaryValidation validation) {
        validationRepository.save(validation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{dictionaryName}")
    public ResponseEntity<Void> deleteDictionary(@PathVariable String dictionaryName) {
        validationRepository.deleteByDictionaryName(dictionaryName);
        return ResponseEntity.noContent().build();
    }
}

