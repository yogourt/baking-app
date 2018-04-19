package com.example.jagoda.bakingapp.model.sync;

import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.model.RecipesApi;
import com.example.jagoda.bakingapp.model.localRepository.RecipesRepository;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class SyncJobService extends JobService {

    RecipesApi recipesApi;

    @Override
    public void onCreate() {
        super.onCreate();

        recipesApi = BakingApp.get(this).getComponent().getRecipesApi();
    }

    @Override
    public boolean onStartJob(final JobParameters job) {

        Timber.d("Job started");

        Call<List<Recipe>> call = recipesApi.getRecipesList();

               call.enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        List<Recipe> recipes = response.body();

                        if (recipes == null) return;
                        RecipesRepository.saveRecipes(recipes);
                        jobFinished(job, false);

                        Timber.d("Job finished");
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        Timber.e(t.getMessage());
                    }
               });

        return true;
    }


    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
