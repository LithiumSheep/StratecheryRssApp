package com.lithiumsheep.stratechery.api;

import com.lithiumsheep.stratechery.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class HttpClient {

    @SuppressWarnings("ConstantConditions")
    private static HttpLoggingInterceptor JakeWharton() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        return logger;
    }

    private static OkHttpClient client;
    static synchronized OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new AlgoliaInterceptor())
                    .addInterceptor(JakeWharton())
                    .build();
        }
        return client;
    }

    private static Retrofit retrofit;
    static synchronized Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(StratecheryAlgoliaSearchService.BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(getClient())
                    .build();
        }
        return retrofit;
    }

    private static StratecheryAlgoliaSearchService service;
    public static synchronized StratecheryAlgoliaSearchService get() {
        if (service == null) {
            service = getRetrofit().create(StratecheryAlgoliaSearchService.class);
        }
        return service;
    }
}
