package com.tyleryin.medialibrary.persistence.mapper;

import com.tyleryin.medialibrary.in_memory_domain.Author;
import com.tyleryin.medialibrary.in_memory_domain.Band;
import com.tyleryin.medialibrary.in_memory_domain.Creator;
import com.tyleryin.medialibrary.in_memory_domain.RecordingArtist;
import com.tyleryin.medialibrary.persistence.entity.AuthorEntity;
import com.tyleryin.medialibrary.persistence.entity.BandEntity;
import com.tyleryin.medialibrary.persistence.entity.CreatorEntity;
import com.tyleryin.medialibrary.persistence.entity.RecordingArtistEntity;

public class CreatorMapper {
    private CreatorMapper() {}

    public static CreatorEntity toEntity(Creator c) {
        if (c instanceof Author a) {
            String first = a.getName().getFirstName();
            String last  = a.getName().getLastName();
            return new AuthorEntity(a.getId(), a.getNameString(), first, last);
        }
        if (c instanceof RecordingArtist ra) {
            String first = ra.getName().getFirstName();
            String last  = ra.getName().getLastName();
            return new RecordingArtistEntity(ra.getId(), ra.getNameString(), first, last);
        }
        if (c instanceof Band b) {
            return new BandEntity(b.getId(), b.getNameString());
        }
        throw new IllegalArgumentException("Unknown creator type: " + c.getClass());
    }
}
