package com.example.jagoda.bakingapp.dependencyInjection.stepList;

import com.example.jagoda.bakingapp.dependencyInjection.app.BakingAppComponent;
import com.example.jagoda.bakingapp.presenter.StepListPresenter;
import com.example.jagoda.bakingapp.view.recipeSteps.StepListActivity;
import com.example.jagoda.bakingapp.view.recipeSteps.StepListFragment;

import dagger.Component;

@StepListScope
@Component(dependencies = BakingAppComponent.class, modules = StepListModule.class)
public interface StepListComponent {

    void injectStepListActivity(StepListActivity activity);
    void injectStepListFragment(StepListFragment fragment);
    void injectStepListPresenter(StepListPresenter presenter);
}
