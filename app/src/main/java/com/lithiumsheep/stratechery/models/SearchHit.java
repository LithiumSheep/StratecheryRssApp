package com.lithiumsheep.stratechery.models;

import com.squareup.moshi.Json;

public class SearchHit {

    @Json(name="post_id")
    String id;

    @Json(name="post_type")
    String type;

    @Json(name="post_type_label")
    String label;

    @Json(name="post_title")
    String title;

    @Json(name="post_excerpt")
    String excerpt;

    @Json(name="post_date")
    String date;

    @Json(name="post_date_formatted")
    String formattedDate;

    @Json(name="permalink")
    String permalink;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public String getTitle() {
        return title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getDate() {
        return date;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String getPermalink() {
        return permalink;
    }
}
