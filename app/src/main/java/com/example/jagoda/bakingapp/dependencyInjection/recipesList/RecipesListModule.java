package com.example.jagoda.bakingapp.dependencyInjection.recipesList;

import android.content.Context;

import com.example.jagoda.bakingapp.dependencyInjection.contextModules.ActivityModule;
import com.example.jagoda.bakingapp.view.recipesList.RecipesListAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@RecipesListScope
@Module(includes = ActivityModule.class)
public class RecipesListModule {

    @Provides
    RecipesListAdapter provideRecipesListAdapter(@Named("activity_context")Context context) {
        return new RecipesListAdapter(context);
    }
}
