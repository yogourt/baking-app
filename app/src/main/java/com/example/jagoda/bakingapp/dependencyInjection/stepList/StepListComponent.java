package com.example.jagoda.bakingapp.dependencyInjection.stepList;

import com.example.jagoda.bakingapp.dependencyInjection.app.BakingAppComponent;
import com.example.jagoda.bakingapp.presenter.StepListPresenter;
import com.example.jagoda.bakingapp.view.stepList.StepListActivity;
import com.example.jagoda.bakingapp.view.stepList.StepListFragment;

import dagger.Component;

@StepListScope
@Component(dependencies = BakingAppComponent.class, modules = StepListModule.class)
public interface StepListComponent {

    void injectStepListActivity(StepListActivity activity);
    void injectStepListFragment(StepListFragment fragment);
    void injectStepListPresenter(StepListPresenter presenter);
}
