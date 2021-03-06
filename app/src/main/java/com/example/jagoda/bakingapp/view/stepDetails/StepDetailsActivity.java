package com.example.jagoda.bakingapp.view.stepDetails;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.dependencyInjection.stepDetails.DaggerStepDetailsComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepDetails.StepDetailsComponent;
import com.example.jagoda.bakingapp.presenter.StepDetailsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_NUM_OF_STEPS;
import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_RECIPE_NAME;
import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_STEP_NUMBER;

public class StepDetailsActivity extends AppCompatActivity implements StepDetailsFragment.ButtonCallback{

    public static final String KEY_STEP_NUMBER = "step_number";

    private String recipeName;
    private int stepNumber;
    private int numOfSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        getSupportActionBar().setBackgroundDrawable(getResources()
                .getDrawable(R.drawable.gradient_background));

        recipeName = getIntent().getStringExtra(KEY_RECIPE_NAME);
        setTitle(recipeName);
        numOfSteps = getIntent().getIntExtra(KEY_NUM_OF_STEPS, 0);

        if(savedInstanceState == null) {
            stepNumber = getIntent().getIntExtra(KEY_STEP_NUMBER, 0);

            StepDetailsFragment fragment = createNewFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_step_details, fragment)
                    .commit();
        } else {
            stepNumber = savedInstanceState.getInt(KEY_STEP_NUMBER);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*if no android:configChanges is declared in the Manifest, onConfigurationChanged must
        be called explicitly */
        onConfigurationChanged(getResources().getConfiguration());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_STEP_NUMBER, stepNumber);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        StepDetailsFragment fragment = (StepDetailsFragment)
                getSupportFragmentManager().getFragments().get(0);
        //make full screen for landscape mode
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE &&
                fragment.isExoPlayerVisible()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().show();
        }
    }


    private StepDetailsFragment createNewFragment() {
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setRecipeName(recipeName);
        fragment.setStepNumber(stepNumber);
        fragment.setNumOfSteps(numOfSteps);
        return fragment;
    }

    @Override
    public void prevButtonClicked() {
        stepNumber--;
        StepDetailsFragment fragment = createNewFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left,
                R.anim.exit_to_right);
        transaction.replace(R.id.fragment_step_details, fragment);
        transaction.commit();
    }

    @Override
    public void nextButtonClicked() {
        Timber.d("next button clicked " + stepNumber);
        stepNumber++;
        StepDetailsFragment fragment = createNewFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right,
                R.anim.exit_to_left);
        transaction.replace(R.id.fragment_step_details, fragment);
        transaction.commit();
    }
}
