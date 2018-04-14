package com.example.jagoda.bakingapp.dependencyInjection.contextModules;

import android.content.Context;

import com.example.jagoda.bakingapp.dependencyInjection.app.BakingAppScope;

import javax.inject.Named;

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

    @Named("application_context")
    @BakingAppScope
    @Provides
    Context provideContext() {
        return appContext;
    }
}
