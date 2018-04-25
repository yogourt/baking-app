package com.example.jagoda.bakingapp.dependencyInjection.recipeList;

import com.example.jagoda.bakingapp.dependencyInjection.app.BakingAppComponent;
import com.example.jagoda.bakingapp.model.sync.FirstSyncUtils;
import com.example.jagoda.bakingapp.presenter.RecipeListPresenter;
import com.example.jagoda.bakingapp.view.recipeList.RecipeListActivity;

import dagger.Component;

@RecipeListScope
@Component(modules = RecipeListModule.class, dependencies = BakingAppComponent.class)
public interface RecipeListComponent {

    void injectRecipesListActivity(RecipeListActivity recipesListActivity);
    void injectRecipesListPresenter(RecipeListPresenter recipesListPresenter);
    void injectFirstSyncUtils(FirstSyncUtils firstSyncUtils);
}
