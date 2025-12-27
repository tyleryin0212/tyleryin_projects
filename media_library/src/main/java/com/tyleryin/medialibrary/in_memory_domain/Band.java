package com.tyleryin.medialibrary.in_memory_domain;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * The {@code Band} class represents a musical group consisting of one or more
 * {@link RecordingArtist} members. A band can serve as the creator of a {@link Music} item
 * in the library catalog.
 * <p>
 * Each band has a unique name and maintains a collection of its members.
 * This class implements the {@link Creator} interface, allowing it to be treated
 * polymorphically alongside {@link RecordingArtist} and {@link Author}.
 *
 */
public class Band implements Creator {

    private final String name;
    private final UUID id;
    private final List<RecordingArtist> members;

    public Band(String name, UUID id, List<RecordingArtist> members) {
        if (name == null) {
            throw new IllegalArgumentException("band name cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("band id cannot be null");
        }
        if (members == null) {
            throw new IllegalArgumentException("band members cannot be null");
        }
        this.name = name;
        this.id = id;
        this.members = members;
    }

    public Band(String name, List<RecordingArtist> members) {
        this(name, UUID.randomUUID(), members);
    }


    public List<RecordingArtist> getMembers() {
        return members;
    }
    @Override
    public String getNameString() {
        return name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public boolean contains(RecordingArtist artist) {
        return members.contains(artist);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Band that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
