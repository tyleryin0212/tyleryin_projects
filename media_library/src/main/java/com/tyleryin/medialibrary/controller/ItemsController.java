package com.tyleryin.medialibrary.controller;

import com.tyleryin.medialibrary.DTO.CreateItemRequest;
import com.tyleryin.medialibrary.DTO.ItemResponse;
import com.tyleryin.medialibrary.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;


/**
 * accepts JSON and returns JSON
 */
@RestController
@RequestMapping("/items")
public class ItemsController {

    private final ItemService itemService;

    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<String> listItems() {
        return itemService.listItemTitles();
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(@Valid @RequestBody CreateItemRequest req) {
        ItemResponse created =  itemService.createItem(req);
        return ResponseEntity
                .created(URI.create("/items/" + created.getId()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getById(@PathVariable UUID id) {
        return itemService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
