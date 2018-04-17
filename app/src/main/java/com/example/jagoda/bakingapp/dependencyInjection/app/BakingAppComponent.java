package com.example.jagoda.bakingapp.dependencyInjection.app;

import com.example.jagoda.bakingapp.model.RecipesApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = BakingAppModule.class)
public interface BakingAppComponent {

    RecipesApi getRecipesApi();
}
