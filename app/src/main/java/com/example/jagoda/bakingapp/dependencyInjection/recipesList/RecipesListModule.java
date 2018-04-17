package com.example.jagoda.bakingapp.dependencyInjection.recipesList;

import android.content.Context;

import com.example.jagoda.bakingapp.presenter.RecipesListPresenter;
import com.example.jagoda.bakingapp.view.recipesList.RecipesListActivity;
import com.example.jagoda.bakingapp.view.recipesList.RecipesListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipesListModule {

    private final RecipesListActivity activity;

    public RecipesListModule(RecipesListActivity activity) {
        this.activity = activity;
    }

    @Provides
    @RecipesListScope
    RecipesListAdapter provideRecipesListAdapter() {
        return new RecipesListAdapter(activity.getBaseContext());
    }

    @Provides
    @RecipesListScope
    RecipesListPresenter provideRecipesListPresenter() {
        return new RecipesListPresenter();
    }
}
