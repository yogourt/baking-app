package com.example.jagoda.bakingapp.presenter;


import com.example.jagoda.bakingapp.model.Ingredient;
import com.example.jagoda.bakingapp.model.localRepository.RecipesRepository;
import com.example.jagoda.bakingapp.view.recipeSteps.StepsListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

public class StepsListPresenter {

    @Inject
    StepsListAdapter adapter;

    public ArrayList<Ingredient> getIngredients(String recipeName) {
        return RecipesRepository.getRecipeIngredients(recipeName);
    }

    public void setRecipeSteps(String recipeName) {
        adapter.setStepsList(RecipesRepository.getRecipeSteps(recipeName));
    }

}
