package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyPopularity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String key;
    private String dictionaryName;
    private Long popularity;

    public String setKey(String key) {
        return this.key;
    }

    public String setDictionaryName(String dictionaryName) {
        return this.dictionaryName;
    }

    public Long setPopularity(long l) {
        return popularity;
    }

    public Long getPopularity() {
        return popularity;
    }
}
