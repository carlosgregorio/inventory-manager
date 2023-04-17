package com.exercise.inventorymanager.item.controller;

import com.exercise.inventorymanager.item.model.Item;
import com.exercise.inventorymanager.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody @Validated Item item) throws URISyntaxException {
        return ResponseEntity.created(new URI("item/" + item.getId())).body(itemService.create(item));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable("itemId") Integer itemId) {
        Optional<Item> optional = itemService.findById(itemId);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage("item not found"));
        }

        return ResponseEntity.ok(optional.get());
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable("itemId") Integer itemId, @RequestBody Item item) {
        Optional<Item> optional = itemService.update(itemId, item);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage("item not found"));
        }

        return ResponseEntity.ok(optional.get());
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable("itemId") Integer itemId) {
        if (!itemService.delete(itemId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage("item not found"));
        }

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorMessage> handleInvalidInput() {
        return ResponseEntity.badRequest().body(createErrorMessage("invalid input"));
    }

    private ErrorMessage createErrorMessage(String message) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(message);
        return errorMessage;
    }
}
