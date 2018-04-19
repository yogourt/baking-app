package com.example.jagoda.bakingapp.dependencyInjection.stepsList;

import android.app.Fragment;

import com.example.jagoda.bakingapp.dependencyInjection.app.BakingAppComponent;
import com.example.jagoda.bakingapp.presenter.StepsListPresenter;
import com.example.jagoda.bakingapp.view.recipeSteps.StepsListFragment;

import dagger.Component;

@StepsListScope
@Component(dependencies = BakingAppComponent.class, modules = StepsListModule.class)
public interface StepsListComponent {

    void injectStepsListFragment(StepsListFragment fragment);
    void injectStepsListPresenter(StepsListPresenter presenter);
}
