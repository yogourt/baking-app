package com.example.jagoda.bakingapp.view.stepDetails;


import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.example.jagoda.bakingapp.dependencyInjection.stepDetails.DaggerStepDetailsComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepDetails.StepDetailsComponent;
import com.example.jagoda.bakingapp.dependencyInjection.stepDetails.StepDetailsModule;
import com.example.jagoda.bakingapp.model.Step;
import com.example.jagoda.bakingapp.presenter.StepDetailsPresenter;
import com.example.jagoda.bakingapp.utils.MimeTypeUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment {

    public static final String KEY_STEP_NUMBER = "step_number";
    public static final String KEY_NUM_OF_STEPS = "num_of_steps";
    public static final String KEY_RECIPE_NAME = "recipe_name";

    private static final String KEY_POSITION = "position";

    @Inject
    StepDetailsPresenter presenter;

    @Inject
    SimpleExoPlayer player;

    @BindView(R.id.step_detailed_desc_text_view)
    TextView detailedDescTv;

    @BindView(R.id.video_view)
    SimpleExoPlayerView exoPlayerView;

    @BindView(R.id.prev_step_button)
    TextView prevStepButton;
    @BindView(R.id.next_step_button)
    TextView nextStepButton;

    private Step recipeStep;
    private int stepNumber;
    private int numOfSteps;
    private String recipeName;

    private boolean displayedInTablet;

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

        displayedInTablet = getActivity().findViewById(R.id.tablet_layout) != null;
        if(getActivity() != null && getActivity().getIntent() != null && !displayedInTablet) {
            recipeName = getActivity().getIntent().getStringExtra(KEY_RECIPE_NAME);
            stepNumber = getActivity().getIntent().getIntExtra(KEY_STEP_NUMBER, 0);
            numOfSteps = getActivity().getIntent().getIntExtra(KEY_NUM_OF_STEPS, 0);

            Timber.d("View preparing");

        }

        if(displayedInTablet) view.setBackgroundColor(getResources().getColor(R.color.gun_powder));

        prepareView();
        prepareButtons();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        player.stop();
        player.release();
    }

    public void prepareView() {

        recipeStep = presenter.getStep(recipeName, stepNumber);
        detailedDescTv.setText(recipeStep.getDescription());

        if(stepNumber == 1 || displayedInTablet) prevStepButton.setVisibility(View.INVISIBLE);
        else prevStepButton.setVisibility(View.VISIBLE);

        if(stepNumber == numOfSteps || displayedInTablet) nextStepButton.setVisibility(View.INVISIBLE);
        else nextStepButton.setVisibility(View.VISIBLE);

        prepareExoPlayer();
    }

    private void prepareButtons() {
        nextStepButton.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stepNumber < numOfSteps) stepNumber++;
                prepareView();
            }
        });
        prevStepButton.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stepNumber > 1) stepNumber--;
                prepareView();
            }
        });
    }

    private void prepareExoPlayer() {

        exoPlayerView.setPlayer(player);

        String videoUrl = recipeStep.getVideoURL();
        String thumbnailUrl = recipeStep.getThumbnailURL();

        //catch if video was passed as a thumbnail
        if(videoUrl.isEmpty() && MimeTypeUtils.isVideoFile(thumbnailUrl)) {
            videoUrl = thumbnailUrl;
        }

        if(videoUrl.isEmpty() || !MimeTypeUtils.isVideoFile(videoUrl)) {
            exoPlayerView.setVisibility(View.GONE);
        } else {
            exoPlayerView.setVisibility(View.VISIBLE);
            MediaSource mediaSource = presenter.getMediaSource(videoUrl);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }

    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public boolean isExoPlayerVisible() {
        return (exoPlayerView.getVisibility() == View.VISIBLE);
    }
}
