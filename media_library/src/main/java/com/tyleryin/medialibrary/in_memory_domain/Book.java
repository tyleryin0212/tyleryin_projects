package com.tyleryin.medialibrary.in_memory_domain;

import java.util.UUID;

/**
 * The {@code Book} class represents a literary work in the library catalog.
 * <p>
 * A book has a title, a year of publication, and an {@link Author} as its creator.
 * This class extends {@link Item} and provides a specific type of catalog entry
 * for written works.
 *
 */
public class Book extends Item{

    /**
     * Constructs a new {@code Book} with the specified author, title, and publication year.
     *
     * @param author the {@link Author} who wrote the book
     * @param title  the title of the book
     * @param year   the year the book was published
     */
    public Book(Author author, String title, int year) {
        super(author, title, year);

    }

    public Book(UUID id, Author author, String newTitle, int newYear) {
        super(id, author, newTitle, newYear);
    }

    public Author getAuthor() {
        return (Author) getCreator();
    }

    @Override
    public boolean matches(ItemPredicate predicate) {
        return predicate.matchesBook(this);
    }



}
