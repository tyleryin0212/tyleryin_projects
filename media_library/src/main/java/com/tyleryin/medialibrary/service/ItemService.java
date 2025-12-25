package com.tyleryin.medialibrary.service;

import com.tyleryin.medialibrary.DTO.CreateItemRequest;
import com.tyleryin.medialibrary.DTO.ItemResponse;
import com.tyleryin.medialibrary.DTO.UpdateItemRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemService {

    ItemResponse createItem(CreateItemRequest req);

    Optional<ItemResponse> getById(UUID id);

    List<ItemResponse> getAll();

    boolean deleteById(UUID id);

    Optional<ItemResponse> updateById(UUID id, UpdateItemRequest req);
}