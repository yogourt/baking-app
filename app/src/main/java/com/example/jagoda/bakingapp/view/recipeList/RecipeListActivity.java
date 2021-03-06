package com.example.jagoda.bakingapp.view.recipeList;

import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.dependencyInjection.recipeList.DaggerRecipeListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.recipeList.RecipeListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.recipeList.RecipeListModule;
import com.example.jagoda.bakingapp.model.sync.FirstSyncUtils;
import com.example.jagoda.bakingapp.presenter.RecipeListPresenter;
import com.example.jagoda.bakingapp.test.SimpleIdlingResource;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity {

    @VisibleForTesting
    @Inject
    SimpleIdlingResource idlingResource;

    @Inject
    RecipeListPresenter presenter;

    @Inject
    RecipeListAdapter adapter;

    @Inject
    FirstSyncUtils utils;

    @BindView(R.id.recipes_list)
    RecyclerView recipesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        RecipeListComponent component = DaggerRecipeListComponent.builder()
                .bakingAppComponent(BakingApp.get(this).getComponent())
                .recipeListModule(new RecipeListModule(this))
                .build();

        component.injectRecipesListActivity(this);
        component.injectRecipesListPresenter(presenter);
        component.injectFirstSyncUtils(utils);

        ButterKnife.bind(this);

        getSupportActionBar().setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.gradient_background));

    }

    //we call it here to make sure that idlingResource is already initialized
    @Override
    protected void onStart() {
        super.onStart();
        prepareRecipesRecyclerView();
    }

    private void prepareRecipesRecyclerView() {
        recipesRecyclerView.setAdapter(adapter);
        recipesRecyclerView.setLayoutManager(presenter.getLayoutManager());
        presenter.setRecipes();
    }

    public SimpleIdlingResource getIdlingResource() {
        return idlingResource;
    }
}
