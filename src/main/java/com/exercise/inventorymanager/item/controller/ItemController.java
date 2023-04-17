package com.exercise.inventorymanager.item.controller;

import com.exercise.inventorymanager.item.model.Item;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item>  getItem(@PathParam("itemId") Integer itemId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item>  updateItem(@PathParam("itemId") Integer itemId, @RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathParam("itemId") Integer itemId)  {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
