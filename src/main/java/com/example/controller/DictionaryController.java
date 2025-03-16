package com.example.controller;

import com.example.model.DictionaryEntry;
import com.example.model.DictionaryValidation;
import com.example.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {
    @Autowired
    private DictionaryService service;

    @GetMapping("/{dictionaryName}")
    public ResponseEntity<Page<DictionaryEntry>> getEntriesPage(
            @PathVariable(required = false) String dictionaryName,
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String value,
            Pageable pageable) {
        try {
            Page<DictionaryEntry> entries = service.getEntriesPage(dictionaryName, key, value, pageable);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/dictionaries")
    public ResponseEntity<List<DictionaryValidation>> getDictionariesSortedByPopularity() {
        try {
            List<DictionaryValidation> dictionaries = service.getDictionariesSortedByPopularity();
            return ResponseEntity.ok(dictionaries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{dictionaryName}/{key}")
    public ResponseEntity<DictionaryEntry> getEntryByKey(@PathVariable String dictionaryName, @PathVariable String key) {
        try {
            return service.getEntryByKey(key, dictionaryName)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{dictionaryName}/{key}")
    public ResponseEntity<Void> deleteEntryByKey(@PathVariable String dictionaryName, @PathVariable String key) {
        try {
            service.deleteEntryByKey(key, dictionaryName);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{dictionaryName}")
    public ResponseEntity<Void> addEntry(@PathVariable String dictionaryName, @RequestBody DictionaryEntry entry) {
        entry.setDictionaryName(dictionaryName);
        try {
            service.addEntry(entry);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}

