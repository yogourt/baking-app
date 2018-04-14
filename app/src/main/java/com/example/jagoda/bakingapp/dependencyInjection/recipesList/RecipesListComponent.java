package com.example.jagoda.bakingapp.dependencyInjection.recipesList;

import com.example.jagoda.bakingapp.dependencyInjection.app.BakingAppComponent;
import com.example.jagoda.bakingapp.view.recipesList.RecipesListActivity;

import dagger.Component;

@RecipesListScope
@Component(modules = RecipesListModule.class, dependencies = BakingAppComponent.class)
public interface RecipesListComponent {

    void injectMainActivity(RecipesListActivity recipesListActivity);
}
