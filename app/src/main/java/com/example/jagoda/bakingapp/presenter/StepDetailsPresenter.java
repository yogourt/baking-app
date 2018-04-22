package com.example.jagoda.bakingapp.presenter;

import android.net.Uri;

import com.example.jagoda.bakingapp.model.localRepository.RecipesRepository;
import com.example.jagoda.bakingapp.model.Step;
import com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import javax.inject.Inject;

import timber.log.Timber;

public class StepDetailsPresenter {

    @Inject
    StepDetailsFragment fragment;

    public Step getStep(String recipeName, int stepNumber) {
        return RecipesRepository.getStep(recipeName, stepNumber);
    }

    public MediaSource getMediaSource(String videoURL) {

        Timber.d(videoURL);
        Uri videoUrl = Uri.parse(videoURL);

        String userAgent = Util.getUserAgent(fragment.getContext(), "BakingApp");

        MediaSource mediaSource = new ExtractorMediaSource(
                videoUrl,
                new DefaultDataSourceFactory(fragment.getContext(), userAgent),
                new DefaultExtractorsFactory(),
                null,
                null
        );
        return mediaSource;
    }

}
