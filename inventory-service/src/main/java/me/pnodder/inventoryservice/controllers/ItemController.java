package me.pnodder.inventoryservice.controllers;

import me.pnodder.inventoryservice.model.Item;
import me.pnodder.inventoryservice.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("items")
public class ItemController {
    private ItemService itemService;
    private Logger log = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public void save(Item item) {
        log.info("here");
        itemService.save(item);
    }

}
