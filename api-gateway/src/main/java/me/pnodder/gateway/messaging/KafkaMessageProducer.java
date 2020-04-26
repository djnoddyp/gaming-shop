package me.pnodder.gateway.messaging;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaMessageProducer implements MessageProducer {

    private final Producer<String, String> producer;

    public KafkaMessageProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    @Override
    public void produce(String topic, String message) {
        producer.send(new ProducerRecord<>(topic, message));
    }

    @Override
    public void close() {
        producer.close();
    }
}
