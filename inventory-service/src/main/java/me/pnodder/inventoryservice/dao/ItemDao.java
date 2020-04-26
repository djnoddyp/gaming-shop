package me.pnodder.inventoryservice.dao;

import me.pnodder.inventoryservice.model.Item;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemDao {
    private MongoTemplate mongoTemplate;

    public ItemDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Item save(Item item) {
        return mongoTemplate.save(item);
    }

    public List<Item> findAll() {
        return mongoTemplate.findAll(Item.class);
    }
}
