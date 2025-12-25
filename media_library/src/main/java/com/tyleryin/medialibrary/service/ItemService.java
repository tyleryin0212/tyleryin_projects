package com.tyleryin.medialibrary.service;

import com.tyleryin.medialibrary.DTO.CreateItemRequest;
import com.tyleryin.medialibrary.DTO.ItemResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemService {
    List<String> listItemTitles();
    ItemResponse createItem(CreateItemRequest req);
    Optional<ItemResponse> getById(UUID id);