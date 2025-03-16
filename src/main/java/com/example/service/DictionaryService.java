package com.example.service;

import com.example.model.DictionaryEntry;
import com.example.model.DictionaryValidation;
import com.example.model.KeyPopularity;
import com.example.repository.DictionaryEntryRepository;
import com.example.repository.DictionaryValidationRepository;
import com.example.repository.KeyPopularityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DictionaryService {
    @Autowired
    private DictionaryEntryRepository repository;
    @Autowired
    private KeyPopularityRepository keyPopularityRepository;
    @Autowired
    private DictionaryValidationRepository validationRepository;
    @Autowired
    private CallbackService callbackService;


    public Page<DictionaryEntry> getEntriesPage(String dictionaryName, String key, String value, Pageable pageable) {
        if (dictionaryName != null) {
            if (key != null) {
                return repository.findByDictionaryNameAndKey(dictionaryName, key, pageable);
            } else if (value != null) {
                return repository.findByDictionaryNameAndValuesContaining(dictionaryName, value, pageable);
            } else {
                return repository.findByDictionaryName(dictionaryName, pageable);
            }
        } else {
            if (key != null) {
                return repository.findByKey(key, pageable);
            } else if (value != null) {
                return repository.findByValuesContaining(value, pageable);
            } else {
                return repository.findAll(pageable);
            }
        }
    }




    private void updateKeyPopularity(String key, String dictionaryName) {
        Optional<KeyPopularity> keyPopularity;
        if (dictionaryName != null) {
            keyPopularity = keyPopularityRepository.findByKeyAndDictionaryName(key, dictionaryName);
        } else {
            keyPopularity = keyPopularityRepository.findByKey(key);
        }
        keyPopularity.ifPresentOrElse(
                kp -> kp.setPopularity(kp.getPopularity() + 1),
                () -> {
                    KeyPopularity newKeyPopularity = new KeyPopularity();
                    newKeyPopularity.setKey(key);
                    newKeyPopularity.setDictionaryName(dictionaryName);
                    newKeyPopularity.setPopularity(1L);
                    keyPopularityRepository.save(newKeyPopularity);
                }
        );
    }

    public List<DictionaryValidation> getDictionariesSortedByPopularity() {
        return validationRepository.findAll(Sort.by(Sort.Direction.DESC, "popularity"));
    }

    public Optional<DictionaryEntry> getEntryByKey(String key, String dictionaryName) {
        return repository.findByKeyAndDictionaryName(key, dictionaryName);
    }

    public void deleteEntryByKey(String key, String dictionaryName) {
        repository.deleteByKeyAndDictionaryName(key, dictionaryName);
        DictionaryEntry entry = new DictionaryEntry();
        entry.setKey(key);
        entry.setDictionaryName(dictionaryName);
        callbackService.notifyExternalSystem(entry, "DELETE");
    }

    public DictionaryEntry addEntry(DictionaryEntry entry) {
        String dictionaryName = entry.getDictionaryName();
        Optional<DictionaryValidation> validation = validationRepository.findByDictionaryName(dictionaryName);
        if (validation.isPresent() && validateEntry(entry, validation.get())) {
            return repository.save(entry);
        } else {
            throw new IllegalArgumentException("Validation failed");
        }
    }




    private boolean validateEntry(DictionaryEntry entry, DictionaryValidation validation) {
        return entry.getKey() != null && !entry.getKey().isEmpty() && entry.getValues() != null && !entry.getValues().isEmpty();
    }
}

