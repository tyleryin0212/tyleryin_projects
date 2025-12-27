package com.tyleryin.medialibrary.in_memory_domain;

import java.util.UUID;

/**
 * The interface represents any entity that can create an {@link Item} in the library's catalog. A creator has a name
 * and maybe one of several types:
 <ul>
 *   <li>{@link Author} — represents an individual who writes books</li>
 *   <li>{@link RecordingArtist} — represents an individual who performs or produces music</li>
 *   <li>{@link Band} — represents a musical group composed of multiple recording artists</li>
 * </ul>
 *
 * <p>This interface is implemented by all possible creator types to provide a consistent
 * way for the {@link Item} and {@link Catalog} classes to access creator information.</p>
 */

public interface Creator {

    /**
     * Returns the name of the creator.
     * <p>
     * For example:
     * <ul>
     *   <li>For an {@link Author} or {@link RecordingArtist}, this may return the individual's full name.</li>
     *   <li>For a {@link Band}, this returns the band's name.</li>
     * </ul>
     *
     * @return the name of the creator as a {@code String}
     */
    String getNameString();
    UUID getId();

}
