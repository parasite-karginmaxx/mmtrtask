package com.example.service;

import com.example.model.CallbackSubscription;
import com.example.model.DictionaryEntry;
import com.example.repository.CallbackSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CallbackService {
    @Autowired
    private CallbackSubscriptionRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    public CallbackSubscription subscribe(CallbackSubscription subscription) {
        return repository.save(subscription);
    }

    public void unsubscribe(Long subscriptionId) {
        repository.deleteById(subscriptionId);
    }

    public void notifyExternalSystem(DictionaryEntry entry, String action) {
        List<CallbackSubscription> subscriptions = repository.findByDictionaryId(entry.getDictionaryName());
        for (CallbackSubscription subscription : subscriptions) {
            try {
                restTemplate.postForEntity(subscription.getCallbackUrl(), entry, Void.class);
            } catch (Exception e) {
                return;
            }
        }
    }
}

