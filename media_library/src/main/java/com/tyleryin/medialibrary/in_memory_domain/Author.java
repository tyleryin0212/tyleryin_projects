package com.tyleryin.medialibrary.in_memory_domain;

import java.util.Objects;
import java.util.UUID;

/**
 * The {@code Author} class represents an individual who has written a book.
 * <p>
 * Each author has a {@link Name}, which includes their first and last names.
 * This class implements the {@link Creator} interface so that it can serve
 * as the creator of a {@link Book} in the library catalog.
 *
 */
public class Author implements Creator {

    /** The name of the author, including first and last names. */
    private final Name name;
    private final UUID id;

    /**
     * Constructs a new {@code Author} with the specified name.
     *
     * @param name the {@link Name} of the author
     */
    public Author(Name name, UUID id) {
        if  (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        this.name = name;
        this.id = id;
    }

    /**
     * convenience: generate id automatically for new domain objects
     * @param name
     */
    public Author(Name name) {
        this(name, UUID.randomUUID());
    }

    /**
     * Returns the full name of the author (first name + last name).
     *
     * @return the author's full name as a {@code String}
     */

    public Name getName() {
        return name;
    }

    @Override
    public String getNameString() {
        return name.getFullName();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
