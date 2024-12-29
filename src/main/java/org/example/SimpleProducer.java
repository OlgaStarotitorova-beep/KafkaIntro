package org.example;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class SimpleProducer {
    public static void main(String[] args) {
        createTopic();
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", Integer.toString(i), "Message " + i);
            producer.send(record);
        }

        producer.close();
    }

    private static void createTopic(){
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try (AdminClient adminClient = AdminClient.create(config)) {
            NewTopic newTopic = new NewTopic("my-topic", 1, (short) 1);
            adminClient.createTopics(Collections.singleton(newTopic)).all().get();
            System.out.println("Топик успешно создан.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}