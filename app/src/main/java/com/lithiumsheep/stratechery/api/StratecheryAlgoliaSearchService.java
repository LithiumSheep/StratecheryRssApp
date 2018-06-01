package com.lithiumsheep.stratechery.api;

import com.lithiumsheep.stratechery.models.SearchQuery;
import com.lithiumsheep.stratechery.models.SearchResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StratecheryAlgoliaSearchService {

    String BASE_URL = "https://q1wkej5qzw-dsn.algolia.net/";

    /*@POST("1/indexes/STRATECHERY_DEVELOPMENT_posts_post/query")
    Call<SearchResult> searchPosts(@Body SearchQuery query);*/

    @POST("1/indexes/STRATECHERY_PRODUCTION_posts_post/query")
    Call<SearchResult> searchPosts(@Body SearchQuery query);
}
