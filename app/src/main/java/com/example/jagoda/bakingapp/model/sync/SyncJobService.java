package com.example.jagoda.bakingapp.model.sync;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.model.RecipesApi;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SyncJobService extends JobService {

    private static final String TAG = "SyncJobService";

    RecipesApi recipesApi;

    private AsyncTask backgroundTask;

    @Override
    public void onCreate() {
        super.onCreate();

        recipesApi = BakingApp.get(this).getComponent().getRecipesApi();
    }

    @Override
    public boolean onStartJob(final JobParameters job) {
        backgroundTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Call<List<Recipe>> call = recipesApi.getRecipesList();

                call.enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        List<Recipe> recipes = response.body();

                        if (recipes == null) return;
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job, false);
            }
        }.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(backgroundTask != null) backgroundTask.cancel(true);
        return true;
    }
}
