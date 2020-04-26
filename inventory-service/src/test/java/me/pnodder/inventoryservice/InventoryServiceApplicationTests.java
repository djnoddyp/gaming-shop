package me.pnodder.inventoryservice;

import me.pnodder.inventoryservice.model.Item;
import me.pnodder.inventoryservice.services.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InventoryServiceApplicationTests {
    @Autowired
    ItemService itemService;

    @Test
    void contextLoads() {
    }

    @Test
    void testMongo() {
        Item item = new Item("", "2070 Super", "Nvidia", 495.99, 8);

        itemService.save(item);

        assertThat(itemService.findAll().size()).isEqualTo(1);
    }

}
