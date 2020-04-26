package me.pnodder.inventoryservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.pnodder.inventoryservice.model.Item;
import me.pnodder.inventoryservice.services.ItemService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Component
public class MessageConsumer {

    private final KafkaConsumer<String, String> consumer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ItemService itemService;
    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);

    public MessageConsumer(ItemService itemService) {
        this.itemService = itemService;
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of(MessagingConstants.INVENTORY_TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                LOG.info("Message received, topic: {}, message: {}", MessagingConstants.INVENTORY_TOPIC, record.value());
                try {
                    Item item = objectMapper.readValue(record.value(), Item.class);
                    itemService.save(item);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
