package com.example.jagoda.bakingapp.model.sync;


import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.model.RecipesApi;
import com.example.jagoda.bakingapp.model.localRepository.RecipesRepository;
import com.example.jagoda.bakingapp.presenter.RecipesListPresenter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class FirstSyncUtils {

    @Inject
    RecipesApi recipesApi;

    @Inject
    RecipesListPresenter presenter;

    public void firstSync() {

        Timber.d("Job started");

        Call<List<Recipe>> call = recipesApi.getRecipesList();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();

                if (recipes == null) return;
                RecipesRepository.saveRecipes(recipes);
                presenter.setRecipes();

                Timber.d("Job finished");
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.e(t.getMessage());
            }
        });
    }
}