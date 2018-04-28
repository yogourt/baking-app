package com.example.jagoda.bakingapp.view.stepList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.dependencyInjection.stepList.DaggerStepListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepList.StepListComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepList.StepListModule;
import com.example.jagoda.bakingapp.presenter.StepListPresenter;
import com.example.jagoda.bakingapp.view.stepDetails.StepDetailsActivity;
import com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment;

import javax.inject.Inject;

import timber.log.Timber;

import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_NUM_OF_STEPS;
import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_RECIPE_NAME;
import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_STEP_NUMBER;


public class StepListActivity extends AppCompatActivity implements StepListAdapter.OnItemClickListener {

    @Inject
    StepListPresenter presenter;

    StepListComponent component;
    private String recipeName;

    private boolean displayedOnTablet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_list);

        component = DaggerStepListComponent.builder()
                .bakingAppComponent(BakingApp.get(this).getComponent())
                .stepListModule(new StepListModule(this))
                .build();

        Timber.d("component initialized");
        component.injectStepListActivity(this);

        recipeName = getIntent().getStringExtra(KEY_RECIPE_NAME);
        setTitle(recipeName);

        getSupportActionBar().setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.gradient_background));

        //determine if tablet layout was created
        if(findViewById(R.id.tablet_layout) != null) {
             displayedOnTablet = true;

             if(savedInstanceState == null) {
                 StepDetailsFragment fragment = new StepDetailsFragment();
                 fragment.setRecipeName(recipeName);
                 fragment.setStepNumber(1);
                 FragmentManager manager = getSupportFragmentManager();
                 manager.beginTransaction()
                         .add(R.id.fragment_step_details_tablet, fragment)
                         .commit();
             }

        }

    }

    public StepListComponent getComponent() {
        return component;
    }

    @Override
    @SuppressLint("RestrictedApi")
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_step_list, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.add_to_widget) {
            presenter.showInWidget();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //onClick method for step item, that opens detailed info about step
    @Override
    public void onClick(int stepNumber, int numOfSteps) {

        if(displayedOnTablet) {
            StepDetailsFragment fragment = new StepDetailsFragment();
            fragment.setRecipeName(recipeName);
            fragment.setStepNumber(stepNumber);
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
