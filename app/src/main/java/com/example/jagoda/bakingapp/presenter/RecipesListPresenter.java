package com.example.jagoda.bakingapp.presenter;


import com.example.jagoda.bakingapp.model.RecipesApi;
import com.example.jagoda.bakingapp.model.localRepository.RecipesRepository;
import com.example.jagoda.bakingapp.view.recipesList.RecipesListAdapter;

import javax.inject.Inject;

public class RecipesListPresenter {

    private static final String TAG = "RecipesListPresenter";


    @Inject
    RecipesListAdapter adapter;

    @Inject
    RecipesApi recipesApi;

    public void setRecipes() {
        RecipesRepository.getRecipesNames();


    }

}
