package com.exercise.inventorymanager.item.repository;

import com.exercise.inventorymanager.item.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, Integer> {
}
