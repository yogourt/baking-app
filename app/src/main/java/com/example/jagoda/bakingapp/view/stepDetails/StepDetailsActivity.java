package com.example.jagoda.bakingapp.view.stepDetails;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.dependencyInjection.stepDetails.DaggerStepDetailsComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepDetails.StepDetailsComponent;
import com.example.jagoda.bakingapp.presenter.StepDetailsPresenter;

import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_RECIPE_NAME;
import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_STEP_NUMBER;

public class StepDetailsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        String recipeName = getIntent().getStringExtra(KEY_RECIPE_NAME);
        setTitle(recipeName);


    }

}
