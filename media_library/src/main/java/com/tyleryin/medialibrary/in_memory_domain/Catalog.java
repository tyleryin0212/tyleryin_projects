package com.tyleryin.medialibrary.in_memory_domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The {@code Catalog} class represents a library catalog that stores a collection of {@link Item} objects,
 * including books and music. It provides functionality to add, remove, and search for items in the catalog
 * based on various criteria such as title keywords, authors, or recording artists.
 * <p>
 * This class supports three types of searches:
 * <ul>
 *   <li>{@link #search(String)} — Searches by a keyword in the title (case-insensitive).</li>
 *   <li>{@link #search(Author)} — Searches for all books by a specific author.</li>
 *   <li>{@link #search(RecordingArtist)} — Searches for all music created by a recording artist or a band
 *       containing that artist.</li>
 * </ul>
 *
 */
public class Catalog {

    private final List<Item> itemList;

    public Catalog() {
        this.itemList = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        itemList.add(item);
    }

    public boolean removeById(UUID id) {
        return itemList.removeIf(item -> Objects.equals(item.getId(), id));
    }

    /**
     * Core search method using double dispatch through ItemPredicate.
     */
    public List<Item> search(ItemPredicate predicate) {
        Objects.requireNonNull(predicate, "Predicate cannot be null");
        List<Item> result = new ArrayList<>();
        for (Item item : itemList) {
            if (item.matches(predicate)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Title search (case-insensitive). Returns empty list for null/blank keyword.
     */
    public List<Item> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return search(new TitleContainsPredicate(keyword));
    }

    /**
     * Finds books by author.
     */
    public List<Item> search(Author author) {
        Objects.requireNonNull(author, "Author cannot be null");
        return search(new ByAuthorPredicate(author));
    }

    /**
     * Finds music created by an artist OR by a band that contains that artist.
     */
    public List<Item> search(RecordingArtist artist) {
        Objects.requireNonNull(artist, "RecordingArtist cannot be null");
        return search(new ByRecordingArtistPredicate(artist));
    }

    /**
     * Optional: convenient read-only access (useful for debugging/tests).
     */
    public List<Item> getAllItems() {
        return List.copyOf(itemList);
    }

    public boolean replaceById(UUID id, Item replacement) {
        for (int idx = 0; idx < itemList.size(); idx++) {
            if (itemList.get(idx).getId().equals(id)) {
                itemList.set(idx, replacement);
                return true;
            }
        }
        return false;
    }
}
