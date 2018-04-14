package com.example.jagoda.bakingapp.dependencyInjection.contextModules;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * This module is providing activity's context for dependency injection
 */

@Module
public class ActivityModule {

    private Context context;

    public ActivityModule(Context context) {
        this.context = context;
    }

    @Named("activity_context")
    @Provides
    Context provideContext() {
        return context;
    }
}