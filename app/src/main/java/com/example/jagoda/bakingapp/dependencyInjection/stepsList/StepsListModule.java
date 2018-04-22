package com.example.jagoda.bakingapp.dependencyInjection.stepsList;

import com.example.jagoda.bakingapp.presenter.StepsListPresenter;
import com.example.jagoda.bakingapp.view.recipeSteps.StepsListActivity;
import com.example.jagoda.bakingapp.view.recipeSteps.StepsListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class StepsListModule {

    private final StepsListActivity activity;

    public StepsListModule(StepsListActivity activity) {
        this.activity = activity;
    }


    @Provides
    @StepsListScope
    StepsListActivity provideStepsListActivity() {
        return activity;
    }
    @Provides
    @StepsListScope
    StepsListPresenter provideStepsListPresenter() {
        return new StepsListPresenter();
    }

    @Provides
    @StepsListScope
    StepsListAdapter provideStepsListAdapter() {
        return new StepsListAdapter(activity.getBaseContext(), activity);
    }

}
