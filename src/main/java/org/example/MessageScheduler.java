package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MessageScheduler {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_NAME = "my_topic9999";

    @Scheduled(fixedRate = 10000)
    public void sendMessage(){
        String message = "Hello Kafka!";
        kafkaTemplate.send(TOPIC_NAME, message);
        System.out.println("Message sent: " + message);
    }

}
