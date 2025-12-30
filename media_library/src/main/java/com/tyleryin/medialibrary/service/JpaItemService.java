package com.tyleryin.medialibrary.service;

import com.tyleryin.medialibrary.DTO.CreateItemRequest;
import com.tyleryin.medialibrary.DTO.ItemResponse;
import com.tyleryin.medialibrary.DTO.ItemType;
import com.tyleryin.medialibrary.DTO.UpdateItemRequest;
import com.tyleryin.medialibrary.in_memory_domain.*;
import com.tyleryin.medialibrary.persistence.entity.*;
import com.tyleryin.medialibrary.persistence.mapper.CreatorMapper;
import com.tyleryin.medialibrary.persistence.repo.CreatorRepository;
import com.tyleryin.medialibrary.persistence.repo.ItemRepository;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Service
public class JpaItemService implements ItemService {

    private final ItemRepository itemRepo;
    private final CreatorRepository creatorRepo;

    public JpaItemService(ItemRepository itemRepo, CreatorRepository creatorRepo) {
        this.itemRepo = itemRepo;
        this.creatorRepo = creatorRepo;
    }

    @Override
    @Transactional
    public ItemResponse createItem(CreateItemRequest request) {
        Item item = toDomain(request);
        CreatorEntity creatorEntity = upsertCreator(item.getCreator());
        ItemEntity saved = itemRepo.save(new ItemEntity(item.getId(), creatorEntity, item.getTitle(), item.getYear()));
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemResponse> getById(UUID id) {
        return itemRepo.findById(id).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> getAll() {
        return itemRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public boolean deleteById(UUID id) {
        if (!itemRepo.existsById(id)) return false;
        itemRepo.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public Optional<ItemResponse> updateById(UUID id, UpdateItemRequest patch) {
        Optional<ItemEntity> opt = itemRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        ItemEntity e = opt.get();

        if (patch.getTitle() != null) e.setTitle(patch.getTitle());
        if (patch.getYear() != null) e.setYear(patch.getYear());

        ItemEntity saved = itemRepo.save(e);
        return Optional.of(toResponse(saved));
    }

    private CreatorEntity upsertCreator(Creator creator) {
        return creatorRepo.findById(creator.getId())
                .orElseGet(() -> creatorRepo.save(CreatorMapper.toEntity(creator)));
    }

    private Item toDomain(CreateItemRequest request) {
        Name name = new Name(request.getFirstName(), request.getLastName());
        return switch (request.getType()) {
            case BOOK -> new Book(new Author(name), request.getTitle(), request.getYear());
            case MUSIC -> new Music(new RecordingArtist(name), request.getTitle(), request.getYear());
        };
    }

    private Item toDomain(ItemEntity e) {
        CreatorEntity c = e.getCreator();

        // Unwrap Hibernate proxy so instanceof works
        c = (CreatorEntity) Hibernate.unproxy(c);

        if (c instanceof AuthorEntity ae) {
            Name name = new Name(ae.getFirstName(), ae.getLastName());
            return new Book(
                    e.getId(),
                    new Author(name, ae.getId()),
                    e.getTitle(),
                    e.getYear()
            );
        }

        if (c instanceof RecordingArtistEntity rae) {
            Name name = new Name(rae.getFirstName(), rae.getLastName());
            return new Music(
                    e.getId(),
                    new RecordingArtist(name, rae.getId()),
                    e.getTitle(),
                    e.getYear()
            );
        }

        if (c instanceof BandEntity be) {
            return new Music(
                    e.getId(),
                    new Band(be.getDisplayName().trim(), be.getId(), List.of()),
                    e.getTitle(),
                    e.getYear()
            );
        }

        throw new IllegalStateException("Unknown creator entity: " + c.getClass());
    }

    private ItemResponse toResponse(ItemEntity entity) {
        return toResponse(toDomain(entity));
    }

    private ItemResponse toResponse(Item item) {
        ItemResponse response = new ItemResponse();
        response.setId(item.getId());
        response.setTitle(item.getTitle());
        response.setYear(item.getYear());

        if (item instanceof Book book) {
            response.setType(ItemType.BOOK);
            response.setDisplayName(book.getAuthor().getDisplayName());
        } else if (item instanceof Music music) {
            response.setType(ItemType.MUSIC);
            response.setDisplayName(music.getCreator().getDisplayName());
        }

        return response;
    }
}