package com.lithiumsheep.stratechery.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class AlgoliaInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        String baseUrl = request.url().scheme() + "://" +  request.url().host() + "/";

        if (baseUrl.equals(StratecheryAlgoliaSearchService.BASE_URL)) {
            requestBuilder.url(
                    request.url().newBuilder()
                            .addQueryParameter("x-algolia-application-id", "Q1WKEJ5QZW")
                            .addQueryParameter("x-algolia-api-key", "9869ac428326cd1e4fec2202453b53e0")
                            .build()
            );
        }

        return chain.proceed(requestBuilder.build());
    }
}
