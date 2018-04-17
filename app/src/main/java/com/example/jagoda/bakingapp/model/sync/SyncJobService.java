package com.example.jagoda.bakingapp.model.sync;

import android.util.Log;

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


public class SyncJobService extends JobService {

    private static final String TAG = "SyncJobService";

    RecipesApi recipesApi;

    @Override
    public void onCreate() {
        super.onCreate();

        recipesApi = BakingApp.get(this).getComponent().getRecipesApi();
    }

    @Override
    public boolean onStartJob(final JobParameters job) {

        Log.d(TAG, "Job started");

        Call<List<Recipe>> call = recipesApi.getRecipesList();

               call.enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        List<Recipe> recipes = response.body();

                        if (recipes == null) return;
                        RecipesRepository.saveRecipes(recipes);
                        jobFinished(job, false);

                        Log.d(TAG, "Job finished");
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
               });

        return true;
    }


    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
