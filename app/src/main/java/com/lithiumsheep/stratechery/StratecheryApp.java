package com.lithiumsheep.stratechery;

import android.app.Application;

import timber.log.Timber;

public class StratecheryApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
