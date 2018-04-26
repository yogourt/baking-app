package com.example.jagoda.bakingapp.model.sync;


import android.support.annotation.VisibleForTesting;

import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.model.RecipeApi;
import com.example.jagoda.bakingapp.model.localRepository.RecipeRepository;
import com.example.jagoda.bakingapp.presenter.RecipeListPresenter;
import com.example.jagoda.bakingapp.test.SimpleIdlingResource;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class FirstSyncUtils {


    @VisibleForTesting
    @Inject
    SimpleIdlingResource idlingResource;

    @Inject
    RecipeApi recipesApi;

    @Inject
    RecipeListPresenter presenter;

    public void firstSync() {

        idlingResource.setIdleState(false);

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

                idlingResource.setIdleState(true);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.e(t.getMessage());
            }
        });
    }
}