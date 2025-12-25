package com.tyleryin.medialibrary.service;


import com.tyleryin.medialibrary.DTO.CreateItemRequest;
import com.tyleryin.medialibrary.DTO.ItemResponse;
import com.tyleryin.medialibrary.DTO.ItemType;
import com.tyleryin.medialibrary.DTO.UpdateItemRequest;
import com.tyleryin.medialibrary.in_memory_domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * decides how to create items, translates DTO to domain
 */
@Service
public class InMemoryItemService implements ItemService {
    private final Catalog catalog;

    public InMemoryItemService(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public ItemResponse createItem(CreateItemRequest req) {
        if (req == null) throw new IllegalArgumentException("Request cannot be null");
        if (req.getType() == null) throw new IllegalArgumentException("type cannot be null");
        if (req.getTitle() == null) throw new IllegalArgumentException("title cannot be null");
        if (req.getFirstName() == null) throw new IllegalArgumentException("firstName cannot be null");
        if (req.getLastName() == null) throw new IllegalArgumentException("lastName cannot be null");

        // Build the domain Name from first/last
        Name name = new Name(req.getFirstName(), req.getLastName());

        Item item;
        ItemType type = req.getType();
        if (type == null) throw new IllegalArgumentException("type cannot be null");

        if (type == ItemType.BOOK) {
            Author author = new Author(name);
            item = new Book(author, req.getTitle(), req.getYear());
        } else if (type == ItemType.MUSIC) {
            RecordingArtist artist = new RecordingArtist(name);
            item = new Music(artist, req.getTitle(), req.getYear());
        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }

        catalog.addItem(item);

        // Map domain -> response DTO
        ItemResponse res = new ItemResponse();
        res.setId(item.getId());
        res.setType(type);
        res.setTitle(item.getTitle());
        res.setYear(item.getYear());

        // Return first/last from the request (safe + consistent with your domain)
        res.setFirstName(req.getFirstName());
        res.setLastName(req.getLastName());

        return res;
    }

    @Override
    public Optional<ItemResponse> getById(UUID id) {
        return catalog.getAllItems().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .map(this::toItemResponse);
    }

    private ItemResponse toItemResponse(Item item) {
        ItemResponse res = new ItemResponse();
        res.setId(item.getId());
        res.setTitle(item.getTitle());
        res.setYear(item.getYear());
        res.setType(item instanceof Book ? ItemType.BOOK : ItemType.MUSIC);

        return res;
    }

    @Override
    public List<ItemResponse> getAll() {
        return catalog.getAllItems().stream()
                .map(this::toItemResponse)
                .toList();
    }

    @Override
    public boolean deleteById(UUID id) {
        return catalog.removeById(id);
    }

    @Override
    public Optional<ItemResponse> updateById(UUID id, UpdateItemRequest req) {
        return catalog.getAllItems().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .flatMap(oldItem -> {
                    String newTitle = (req.getTitle() != null) ? req.getTitle() : oldItem.getTitle();
                    int newYear = (req.getYear() != null) ? req.getYear() : oldItem.getYear();

                    Item replacement;
                    if (oldItem instanceof Book b) {
                        replacement = new Book(oldItem.getId(), b.getAuthor(), newTitle, newYear); // needs ctor that accepts id
                    } else if (oldItem instanceof Music m) {
                        replacement = new Music(oldItem.getId(), m.getCreator(), newTitle, newYear); // same idea
                    } else {
                        return Optional.empty();
                    }

                    boolean ok = catalog.replaceById(id, replacement);
                    return ok ? Optional.of(toItemResponse(replacement)) : Optional.empty();
                });
    }
}
