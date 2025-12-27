package com.tyleryin.medialibrary.in_memory_domain;

import java.util.Locale;
import java.util.Objects;

public class ByCreatorNameContainsPredicate implements ItemPredicate {
    private final String needle;

    public ByCreatorNameContainsPredicate(String keyword) {
        Objects.requireNonNull(keyword, "Keyword cannot be null");
        if (keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be blank");
        }
        this.needle = keyword.toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean matchesBook(Book b) {
        return creatorNameMatches(b.getCreator());
    }

    @Override
    public boolean matchesMusic(Music m) {
        return creatorNameMatches(m.getCreator());
    }

    private boolean creatorNameMatches(Creator creator) {
        String name = creator.getNameString();
        if (name == null) return false; // defensive (shouldn't happen if your Name class is solid)
        return name.toLowerCase(Locale.ROOT).contains(needle);
    }
}
