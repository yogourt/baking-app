package com.example.jagoda.bakingapp.view.stepDetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.dependencyInjection.stepDetails.DaggerStepDetailsComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepDetails.StepDetailsComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepDetails.StepDetailsModule;
import com.example.jagoda.bakingapp.model.Step;
import com.example.jagoda.bakingapp.presenter.StepDetailsPresenter;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment {

    public static final String KEY_STEP_NUMBER = "step_number";
    public static final String KEY_NUM_OF_STEPS = "num_of_steps";
    public static final String KEY_RECIPE_NAME = "recipe_name";

    @Inject
    StepDetailsPresenter presenter;

    @Inject
    SimpleExoPlayer player;

    @BindView(R.id.step_detailed_desc_text_view)
    TextView detailedDescTv;

    @BindView(R.id.video_view)
    SimpleExoPlayerView exoPlayerView;

    private Step recipeStep;
    private int stepNumber;
    private int numOfSteps;
    private String recipeName;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        StepDetailsComponent component = DaggerStepDetailsComponent.builder()
                .stepDetailsModule(new StepDetailsModule(this))
                .bakingAppComponent(BakingApp.get(this).getComponent())
                .build();

        component.injectStepDetailsFragment(this);
        component.injectStepDetailsPresenter(presenter);

        ButterKnife.bind(this, view);


        if(getActivity() != null && getActivity().getIntent() != null) {
            recipeName = getActivity().getIntent().getStringExtra(KEY_RECIPE_NAME);
            stepNumber = getActivity().getIntent().getIntExtra(KEY_STEP_NUMBER, 0);
            numOfSteps = getActivity().getIntent().getIntExtra(KEY_NUM_OF_STEPS, 0);

            prepareStepDescription();

            view.setOnTouchListener(new OnSwipeTouchListener(getContext()){

                @Override
                public void onSwipeRight() {
                    if(stepNumber > 1) {
                        stepNumber -= 1;
                        prepareStepDescription();
                        prepareExoPlayer();
                    }
                }

                @Override
                public void onSwipeLeft() {
                    if(stepNumber < numOfSteps) {
                        stepNumber += 1;
                        prepareStepDescription();
                        prepareExoPlayer();
                    }
                }
            });

        }

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        prepareExoPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        player.stop();
        player.release();
    }

    private void prepareStepDescription() {
        recipeStep = presenter.getStep(recipeName, stepNumber);
        detailedDescTv.setText(recipeStep.getDescription());
    }

    private void prepareExoPlayer() {

        exoPlayerView.setPlayer(player);

        String videoUrl = recipeStep.getVideoURL();

        if(videoUrl.isEmpty()) {
            exoPlayerView.setVisibility(View.GONE);
        } else {
            MediaSource mediaSource = presenter.getMediaSource(videoUrl);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

}
