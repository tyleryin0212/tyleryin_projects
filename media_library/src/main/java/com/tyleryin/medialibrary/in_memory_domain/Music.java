package com.tyleryin.medialibrary.in_memory_domain;

import java.util.UUID;

/**
 * The {@code Music} class represents a musical recording or composition in the library catalog.
 * <p>
 * A music item has a title, a year of release, and a creator that may be either a
 * {@link RecordingArtist} (for solo works) or a {@link Band} (for group works).
 * This class extends {@link Item} to provide a specific catalog entry for musical works.
 *
 *
 */
public class Music extends Item{

    /**
     * Constructs a new {@code Music} item created by an individual {@link RecordingArtist}.
     *
     * @param artist the recording artist who created the music
     * @param title  the title of the music
     * @param year   the year the music was released
     */

    public Music(RecordingArtist artist, String title, int year) {
        super(artist, title, year);
    }

    /**
     * Constructs a new {@code Music} item created by a {@link Band}.
     *
     * @param band  the band that created the music
     * @param title the title of the music
     * @param year  the year the music was released
     */
    public Music(Band band, String title, int year) {
        super(band, title, year);
    }

    public Music(UUID id, Creator creator, String newTitle, int newYear) {
        super(id, creator, newTitle, newYear);
    }

    public RecordingArtist getArtist() {
        if (this.getCreator().getClass() == RecordingArtist.class) {
            return (RecordingArtist) this.getCreator();
        }
        return null;
    }

    public Band getBand() {
        if (this.getCreator().getClass() == Band.class){
            return (Band) this.getCreator();
        }
        return null;
    }


    public boolean matches(ItemPredicate predicate) {
        return predicate.matchesMusic(this);
    }


}
