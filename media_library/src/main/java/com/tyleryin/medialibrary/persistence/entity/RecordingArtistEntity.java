package com.tyleryin.medialibrary.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "recording_artists")
@DiscriminatorValue("RECORDING_ARTIST")
public class RecordingArtistEntity extends CreatorEntity{
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    protected RecordingArtistEntity() {}

    public RecordingArtistEntity(UUID id, String displayName, String firstName, String lastName) {
        super(id, displayName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
