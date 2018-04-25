package com.example.jagoda.bakingapp.dependencyInjection.stepList;

import com.example.jagoda.bakingapp.presenter.StepListPresenter;
import com.example.jagoda.bakingapp.view.recipeSteps.StepListActivity;
import com.example.jagoda.bakingapp.view.recipeSteps.StepListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class StepListModule {

    private final StepListActivity activity;

    public StepListModule(StepListActivity activity) {
        this.activity = activity;
    }


    @Provides
    @StepListScope
    StepListActivity provideStepsListActivity() {
        return activity;
    }

    @Provides
    @StepListScope
    StepListPresenter provideStepsListPresenter() {
        return new StepListPresenter();
    }

    @Provides
    @StepListScope
    StepListAdapter provideStepsListAdapter() {
        return new StepListAdapter(activity.getBaseContext(), activity);
    }

}
