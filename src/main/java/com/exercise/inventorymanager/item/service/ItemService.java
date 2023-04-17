package com.exercise.inventorymanager.item.service;

import com.exercise.inventorymanager.item.model.Item;
import com.exercise.inventorymanager.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> findById(Integer itemId) {
        return itemRepository.findById(itemId);
    }

    public Optional<Item> update(Integer itemId, Item item) {
        Optional<Item> optional = itemRepository.findById(itemId).map(toUpdate -> {
            toUpdate.setQuantity(item.getQuantity());
            toUpdate.setName(item.getName());
            toUpdate.setValue(item.getValue());
            return itemRepository.save(toUpdate);
        });

        return optional;
    }

    public Boolean delete(Integer itemId) {
        return itemRepository.findById(itemId).map(item -> {
            itemRepository.delete(item);
            return true;
        }).orElse(false);
    }
}
