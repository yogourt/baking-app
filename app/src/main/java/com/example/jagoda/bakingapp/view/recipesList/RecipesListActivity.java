package com.example.jagoda.bakingapp.view.recipesList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.dependencyInjection.recipesList.DaggerRecipesListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.recipesList.RecipesListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.recipesList.RecipesListModule;
import com.example.jagoda.bakingapp.model.sync.FirstSyncUtils;
import com.example.jagoda.bakingapp.presenter.RecipesListPresenter;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListActivity extends AppCompatActivity {

    private static final int COLUMN_NUM_IN_TABLET = 3;

    @Inject
    RecipesListPresenter presenter;

    @Inject
    RecipesListAdapter adapter;

    @Inject
    FirstSyncUtils utils;

    @BindView(R.id.recipes_list)
    RecyclerView recipesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        RecipesListComponent component = DaggerRecipesListComponent.builder()
                .bakingAppComponent(BakingApp.get(this).getComponent())
                .recipesListModule(new RecipesListModule(this))
                .build();

        component.injectRecipesListActivity(this);
        component.injectRecipesListPresenter(presenter);
        component.injectFirstSyncUtils(utils);

        ButterKnife.bind(this);

        getSupportActionBar().setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.gradient_background));

        prepareRecipesRecyclerView();

    }

    private void prepareRecipesRecyclerView() {
        recipesRecyclerView.setAdapter(adapter);

        if(getResources().getBoolean(R.bool.displayed_in_tablet)) {
            recipesRecyclerView.setLayoutManager(new GridLayoutManager(this, COLUMN_NUM_IN_TABLET));
        } else {
            recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        presenter.setRecipes();
    }
}
