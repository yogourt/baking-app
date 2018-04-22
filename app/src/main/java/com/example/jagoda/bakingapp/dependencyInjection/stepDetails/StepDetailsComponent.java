package com.example.jagoda.bakingapp.dependencyInjection.stepDetails;

import com.example.jagoda.bakingapp.dependencyInjection.app.BakingAppComponent;
import com.example.jagoda.bakingapp.presenter.StepDetailsPresenter;
import com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment;

import dagger.Component;

@StepDetailsScope
@Component(dependencies = BakingAppComponent.class, modules = StepDetailsModule.class)
public interface StepDetailsComponent {

    void injectStepDetailsFragment(StepDetailsFragment stepDetailsFragment);
    void injectStepDetailsPresenter(StepDetailsPresenter stepDetailsPresenter);
}
