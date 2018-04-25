package com.example.jagoda.bakingapp.presenter;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.model.RecipeApi;
import com.example.jagoda.bakingapp.model.Step;
import com.example.jagoda.bakingapp.model.localRepository.RecipeRepository;
import com.example.jagoda.bakingapp.model.sync.FirstSyncUtils;
import com.example.jagoda.bakingapp.view.recipeList.RecipeListActivity;
import com.example.jagoda.bakingapp.view.recipeList.RecipeListAdapter;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.example.jagoda.bakingapp.presenter.StepListPresenter.SHARED_PREFERENCES_NAME;
import static com.example.jagoda.bakingapp.widget.ListRemoteViewFactory.KEY_RECIPE_NAME;

public class RecipeListPresenter {

    private static final int COLUMN_NUM_IN_TABLET = 3;
    private static final int COLUMN_NUM_IN_LANDSCAPE = 2;

    @Inject
    RecipeListActivity activity;

    @Inject
    RecipeListAdapter adapter;

    @Inject
    RecipeApi recipesApi;

    @Inject
    FirstSyncUtils firstSyncUtils;

    public void setRecipes() {
        List<Recipe> recipesNames = RecipeRepository.getRecipes();
        if(recipesNames.isEmpty()) firstSync();
        else adapter.setRecipesList(recipesNames);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        if(activity.getResources().getBoolean(R.bool.displayed_in_tablet)) {
            return new GridLayoutManager(activity, COLUMN_NUM_IN_TABLET);
        } else if(activity.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(activity, COLUMN_NUM_IN_LANDSCAPE);
        } else {
            return new LinearLayoutManager(activity);
        }
    }

    private void firstSync() {
        firstSyncUtils.firstSync();
    }

    public void prepareSharedPreferences() {
        SharedPreferences preferences = activity.getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
            preferences.edit().putString(KEY_RECIPE_NAME, RecipeRepository.getFirstRecipeName())
                    .apply();

    }
}