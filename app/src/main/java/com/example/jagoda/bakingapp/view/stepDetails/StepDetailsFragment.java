package com.example.jagoda.bakingapp.view.stepDetails;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

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

    private static final String KEY_VIDEO_POSITION = "video_position";
    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";

    @Inject
    StepDetailsPresenter presenter;

    @Inject
    SimpleExoPlayer player;

    @BindView(R.id.step_detailed_desc_text_view)
    TextView detailedDescTv;

    @BindView(R.id.video_view)
    SimpleExoPlayerView exoPlayerView;

    @BindView(R.id.thumbnail_image_view)
    ImageView thumbnailImageView;

    @BindView(R.id.prev_step_button)
    TextView prevStepButton;
    @BindView(R.id.next_step_button)
    TextView nextStepButton;

    private StepDetailsComponent component;

    private Step recipeStep;
    private int stepNumber;
    private int numOfSteps;
    private String recipeName;

    private long videoPosition;
    private boolean playWhenReady;
    private boolean restoreInstanceState;
    private boolean restoreFromBackground;

    private ButtonCallback buttonCallback;

    private boolean displayedInTablet;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        component = DaggerStepDetailsComponent.builder()
                .stepDetailsModule(new StepDetailsModule(this))
                .bakingAppComponent(BakingApp.get(this).getComponent())
                .build();

        component.injectStepDetailsFragment(this);
        component.injectStepDetailsPresenter(presenter);

        ButterKnife.bind(this, view);

        if(savedInstanceState != null) {
            restoreInstanceState = true;
            recipeName = savedInstanceState.getString(KEY_RECIPE_NAME);
            stepNumber = savedInstanceState.getInt(KEY_STEP_NUMBER);
            numOfSteps = savedInstanceState.getInt(KEY_NUM_OF_STEPS);
            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            videoPosition = savedInstanceState.getLong(KEY_VIDEO_POSITION);
        }

        displayedInTablet = getActivity().findViewById(R.id.tablet_layout) != null;

        if(displayedInTablet) view.setBackgroundColor(getResources().getColor(R.color.gun_powder));

        if(!displayedInTablet) {
            buttonCallback = (ButtonCallback) getActivity();


            view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

                @Override
                public void onSwipeRight() {
                    if (stepNumber > 1) {
                        buttonCallback.prevButtonClicked();
                    }
                }

                @Override
                public void onSwipeLeft() {
                    if (stepNumber < numOfSteps) {
                        buttonCallback.nextButtonClicked();
                    }
                }
            });
        }


        prepareView();
        prepareButtons();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseExoPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareExoPlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_RECIPE_NAME, recipeName);
        outState.putInt(KEY_STEP_NUMBER, stepNumber);
        outState.putInt(KEY_NUM_OF_STEPS, numOfSteps);
        outState.putLong(KEY_VIDEO_POSITION, videoPosition);
        outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReady);
        super.onSaveInstanceState(outState);
    }

    public void prepareView() {

        recipeStep = presenter.getStep(recipeName, stepNumber);
        detailedDescTv.setText(recipeStep.getDescription());

        String thumbnailUrl = recipeStep.getThumbnailURL();

        if(!TextUtils.isEmpty(thumbnailUrl) && MimeTypeUtils.isImageFile(thumbnailUrl)) {
            Picasso.get()
                    .load(Uri.parse(thumbnailUrl))
                    .into(thumbnailImageView);
        } else thumbnailImageView.setVisibility(View.GONE);

        if(stepNumber == 1 || displayedInTablet) prevStepButton.setVisibility(View.INVISIBLE);
        else prevStepButton.setVisibility(View.VISIBLE);

        if(stepNumber == numOfSteps || displayedInTablet) nextStepButton.setVisibility(View.INVISIBLE);
        else nextStepButton.setVisibility(View.VISIBLE);
    }

    private void prepareExoPlayer() {

        player = component.getSimpleExoPlayer();

        exoPlayerView.setPlayer(player);

        String videoUrl = recipeStep.getVideoURL();

        if(videoUrl.isEmpty() || !MimeTypeUtils.isVideoFile(videoUrl)) {
            exoPlayerView.setVisibility(View.GONE);
        } else {
            exoPlayerView.setVisibility(View.VISIBLE);
            MediaSource mediaSource = presenter.getMediaSource(videoUrl);

            if(restoreInstanceState || restoreFromBackground) {
                player.prepare(mediaSource, false, false );
                player.seekTo(0, videoPosition);
                player.setPlayWhenReady(playWhenReady);
            }
            else {
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);
            }
        }

    }

    private void releaseExoPlayer() {
        videoPosition = player.getCurrentPosition();
        restoreFromBackground = true;
        playWhenReady = player.getPlayWhenReady();
        player.stop();
        player.release();
        player = null;
    }

    //interface implemented by host activity
    public interface ButtonCallback {
        void prevButtonClicked();
        void nextButtonClicked();
    }

    private void prepareButtons() {

        nextStepButton.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stepNumber < numOfSteps) {
                    buttonCallback.nextButtonClicked();
                }
            }
        });
        prevStepButton.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stepNumber > 1) {
                    buttonCallback.prevButtonClicked();
                }
            }
        });
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setNumOfSteps(int numOfSteps) {
        this.numOfSteps = numOfSteps;
    }

    public boolean isExoPlayerVisible() {
        return (exoPlayerView.getVisibility() == View.VISIBLE);
    }
}
