package com.example.jagoda.bakingapp.dependencyInjection.app;


import com.example.jagoda.bakingapp.model.RecipeApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class BakingAppModule {

    @Provides
    RecipeApi provideRecipesApi(Retrofit retrofit) {
        return retrofit.create(RecipeApi.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
