package com.example.controller;

import com.example.model.CallbackSubscription;
import com.example.service.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dictionaries/callbacks")
public class CallbackController {
    @Autowired
    private CallbackService service;

    @PostMapping("/subscriptions")
    public ResponseEntity<Map<String, String>> subscribe(@RequestBody CallbackSubscription subscription) {
        CallbackSubscription savedSubscription = service.subscribe(subscription);
        Map<String, String> response = new HashMap<>();
        response.put("subscriptionId", savedSubscription.getId().toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/subscriptions")
    public ResponseEntity<Void> unsubscribe(@RequestParam Long subscriptionId) {
        service.unsubscribe(subscriptionId);
        return ResponseEntity.noContent().build();
    }
}
