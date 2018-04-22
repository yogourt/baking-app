package com.example.jagoda.bakingapp.presenter;


import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.model.RecipesApi;
import com.example.jagoda.bakingapp.model.localRepository.RecipesRepository;
import com.example.jagoda.bakingapp.model.sync.FirstSyncUtils;
import com.example.jagoda.bakingapp.view.recipesList.RecipesListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RecipesListPresenter {

    @Inject
    RecipesListAdapter adapter;

    @Inject
    RecipesApi recipesApi;

    @Inject
    FirstSyncUtils firstSyncUtils;

    public void setRecipes() {
        List<String> recipesNames = RecipesRepository.getRecipesNames();
        if(recipesNames.isEmpty()) firstSync();
        else adapter.setRecipesList(recipesNames);
    }

    private void firstSync() {
        firstSyncUtils.firstSync();
    }

}