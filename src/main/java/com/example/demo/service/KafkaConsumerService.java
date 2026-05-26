package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "expense-topic", groupId = "expense-group")
    public void consumeMessage(String message) {

        System.out.println("Consumed Message: " + message);
    }
}
