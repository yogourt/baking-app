package com.example.jagoda.bakingapp.view.recipeSteps;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.dependencyInjection.stepsList.DaggerStepsListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepsList.StepsListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepsList.StepsListModule;
import com.example.jagoda.bakingapp.view.stepDetails.StepDetailsActivity;
import com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment;

import timber.log.Timber;

import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_NUM_OF_STEPS;
import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_RECIPE_NAME;
import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_STEP_NUMBER;


public class StepsListActivity extends AppCompatActivity implements StepsListAdapter.OnItemClickListener {

    StepsListComponent component;
    private String recipeName;

    private boolean displayedOnTablet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_list);

        component = DaggerStepsListComponent.builder()
                .bakingAppComponent(BakingApp.get(this).getComponent())
                .stepsListModule(new StepsListModule(this))
                .build();

        Timber.d("component initialized");

        recipeName = getIntent().getStringExtra(KEY_RECIPE_NAME);
        setTitle(recipeName);

        getSupportActionBar().setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.gradient_background));

        //determine if tablet layout was created
        if(findViewById(R.id.tablet_layout) != null) {
             displayedOnTablet = true;

            StepDetailsFragment fragment = new StepDetailsFragment();
            fragment.setStepNumber(1);
            fragment.setRecipeName(recipeName);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.fragment_step_details_tablet, fragment)
                    .commit();

        }

    }

    public StepsListComponent getComponent() {
        return component;
    }

    //onClick method for step item, that opens detailed info about step
    @Override
    public void onClick(int stepNumber, int numOfSteps) {

        if(displayedOnTablet) {
            StepDetailsFragment fragment = new StepDetailsFragment();
            fragment.setStepNumber(stepNumber);
            fragment.setRecipeName(recipeName);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fragment_step_details_tablet, fragment)
                    .commit();
        } else {
            Intent stepDetailsActivityIntent = new Intent(this, StepDetailsActivity.class);
            stepDetailsActivityIntent.putExtra(KEY_RECIPE_NAME, recipeName);
            stepDetailsActivityIntent.putExtra(KEY_STEP_NUMBER, stepNumber);
            stepDetailsActivityIntent.putExtra(KEY_NUM_OF_STEPS, numOfSteps);
            startActivity(stepDetailsActivityIntent);
        }
    }
}
