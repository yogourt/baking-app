package com.example.jagoda.bakingapp.model.sync;


import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.model.RecipeApi;
import com.example.jagoda.bakingapp.model.localRepository.RecipeRepository;
import com.example.jagoda.bakingapp.presenter.RecipeListPresenter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class FirstSyncUtils {

    @Inject
    RecipeApi recipesApi;

    @Inject
    RecipeListPresenter presenter;

    public void firstSync() {

        Timber.d("Job started");

        Call<List<Recipe>> call = recipesApi.getRecipesList();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();

                if (recipes == null) return;
                RecipeRepository.saveRecipes(recipes);
                presenter.setRecipes();
                presenter.prepareSharedPreferences();

                Timber.d("Job finished");
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.e(t.getMessage());
            }
        });
    }
}