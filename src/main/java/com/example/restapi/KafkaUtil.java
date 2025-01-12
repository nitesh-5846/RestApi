package main.java.com.example.restapi;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;
import java.util.Collections;
import java.time.Duration;

public class KafkaUtil {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    // Kafka Producer
    public static void sendMessage(String topic, String key, String value) {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("Message sent successfully! Offset: " + metadata.offset());
            } else {
                System.out.println("Error sending message: " + exception.getMessage());
            }
        });

        producer.close();
    }

    // Kafka Consumer
    public static void consumeMessages(String topic, String groupId, MessageProcessor processor) {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", groupId);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                processor.process(record.value());
            }
        }
    }

    // Functional interface for processing messages
    public interface MessageProcessor {
        void process(String message);
    }
}