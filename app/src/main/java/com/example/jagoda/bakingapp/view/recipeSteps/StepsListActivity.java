package com.example.jagoda.bakingapp.view.recipeSteps;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.dependencyInjection.stepsList.DaggerStepsListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepsList.StepsListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepsList.StepsListModule;

import timber.log.Timber;


public class StepsListActivity extends AppCompatActivity {

    StepsListComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_list);

        component = DaggerStepsListComponent.builder()
                .bakingAppComponent(BakingApp.get(this).getComponent())
                .stepsListModule(new StepsListModule(this))
                .build();

        Timber.d("component initialized");
    }

    public StepsListComponent getComponent() {
        return component;
    }
}
