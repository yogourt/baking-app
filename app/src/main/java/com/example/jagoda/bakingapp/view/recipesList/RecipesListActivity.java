package com.example.jagoda.bakingapp.view.recipesList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.dependencyInjection.recipesList.DaggerRecipesListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.recipesList.RecipesListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.contextModules.ActivityModule;

import javax.inject.Inject;

public class RecipesListActivity extends AppCompatActivity {

    @Inject
    RecipesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipesListComponent component = DaggerRecipesListComponent.builder()
                .bakingAppComponent(BakingApp.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build();

        component.injectMainActivity(this);


        adapter.getItemCount();

    }
}
