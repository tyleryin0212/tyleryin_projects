package com.tyleryin.medialibrary.in_memory_domain;

import java.util.Objects;
import java.util.UUID;

/**
 * The {@code RecordingArtist} class represents an individual who performs or produces music.
 * <p>
 * Each recording artist has a {@link Name}, which includes their first and last names.
 * This class implements the {@link Creator} interface so that a recording artist can serve
 * as the creator of a {@link Music} item in the library catalog.
 * <p>
 * A {@code RecordingArtist} may also be a member of a {@link Band}.
 *
 */
public class RecordingArtist implements Creator {

    /** The name of the recording artist, including first and last names. */
    private final Name name;

    private final UUID id;

    public RecordingArtist(Name name, UUID id) {
        if  (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.name = name;
        this.id = id;
    }

    /**
     * Constructs a new {@code RecordingArtist} with the specified name.
     *
     * @param name the {@link Name} of the recording artist
     */
    public RecordingArtist(Name name) {
        this(name, UUID.randomUUID());
    }

    /**
     * Returns the full name of this recording artist (first name + last name).
     *
     * @return the artist's full name as a {@code String}
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
        if (!(o instanceof RecordingArtist that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
