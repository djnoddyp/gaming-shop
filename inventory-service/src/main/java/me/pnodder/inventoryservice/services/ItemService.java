package me.pnodder.inventoryservice.services;

import me.pnodder.inventoryservice.dao.ItemDao;
import me.pnodder.inventoryservice.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> findAll() {
        return itemDao.findAll();
    }

    public Item save(Item item) {
        return itemDao.save(item);
    }
}
