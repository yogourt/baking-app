package com.example.jagoda.bakingapp.dependencyInjection.app;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This module is providing app's context for dependency injection
 */

@Module
public class ContextModule {

    private Context appContext;

    public ContextModule(Context context) {
        appContext = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return appContext;
    }
}
