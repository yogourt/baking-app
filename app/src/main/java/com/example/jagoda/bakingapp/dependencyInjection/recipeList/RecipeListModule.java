package com.example.jagoda.bakingapp.dependencyInjection.recipeList;

import com.example.jagoda.bakingapp.model.sync.FirstSyncUtils;
import com.example.jagoda.bakingapp.presenter.RecipeListPresenter;
import com.example.jagoda.bakingapp.view.recipeList.RecipeListActivity;
import com.example.jagoda.bakingapp.view.recipeList.RecipeListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeListModule {

    private final RecipeListActivity activity;

    public RecipeListModule(RecipeListActivity activity) {
        this.activity = activity;
    }

    @Provides
    @RecipeListScope
    RecipeListActivity provideRecipeListActivity() {
        return activity;
    }

    @Provides
    @RecipeListScope
    RecipeListAdapter provideRecipesListAdapter() {
        return new RecipeListAdapter(activity.getBaseContext());
    }

    @Provides
    @RecipeListScope
    RecipeListPresenter provideRecipesListPresenter() {
        return new RecipeListPresenter();
    }

    @Provides
    @RecipeListScope
    FirstSyncUtils provideFirstSyncUtils() {
        return new FirstSyncUtils();
    }
}
