package com.lithiumsheep.stratechery.models;

import java.util.Objects;

public final class SearchQuery {

    private String query;
    private int hitsPerPage = 5;

    public SearchQuery() {
        // default empty constructor for moshi to allow default field values
    }

    public SearchQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public int getHitsPerPage() {
        return hitsPerPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchQuery that = (SearchQuery) o;
        return Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query);
    }
}
