package com.example.jagoda.bakingapp.dependencyInjection.stepDetails;

import com.example.jagoda.bakingapp.presenter.StepDetailsPresenter;
import com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import dagger.Module;
import dagger.Provides;

@Module
public class StepDetailsModule {

    private StepDetailsFragment fragment;

    public StepDetailsModule(StepDetailsFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @StepDetailsScope
    StepDetailsFragment provideStepDetailsFragment() {
        return fragment;
    }

    @Provides
    @StepDetailsScope
    StepDetailsPresenter provideStepDetailsPresenter() {
        return new StepDetailsPresenter();
    }

    @Provides
    SimpleExoPlayer provideSimpleExoPlayer() {

        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(
                fragment.getContext(),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );

        return player;
    }
}
