package com.lithiumsheep.stratechery.models;

import java.util.Objects;

public class SearchQuery {

    String query;
    int hitsPerPage = 5;

    public SearchQuery() {

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
