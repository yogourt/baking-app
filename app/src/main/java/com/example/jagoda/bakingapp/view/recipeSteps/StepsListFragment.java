package com.example.jagoda.bakingapp.view.recipeSteps;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.dependencyInjection.stepsList.StepsListComponent;
import com.example.jagoda.bakingapp.presenter.StepsListPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


/**
 *
 */
public class StepsListFragment extends Fragment {

    public static final String KEY_RECIPE_NAME = "recipe_name";

    private String recipeName;

    @Inject
    StepsListPresenter presenter;

    @Inject
    StepsListAdapter adapter;

    @BindView(R.id.ingredients_list_view)
    ExpandableListView ingredientsListView;
    @BindView(R.id.steps_recycler_view)
    RecyclerView stepsRecyclerView;

    public StepsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_list, container, false);

        if(getActivity() != null && getActivity().getIntent() != null) {
            recipeName = getActivity().getIntent().getStringExtra(KEY_RECIPE_NAME);
        }

        ButterKnife.bind(this, view);

        if(presenter==null) Timber.d("Presenter is null");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null) {
            StepsListComponent component = ((StepsListActivity) getActivity()).getComponent();
            component.injectStepsListFragment(this);
            component.injectStepsListPresenter(presenter);
        }

        //this calls mast be done after fragment is injected as methods uses presenter
        prepareIngredientsList();
        prepareStepsRecyclerView();
    }

    private void prepareIngredientsList() {

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(getContext(),
                presenter.getIngredientsTitle(),
                R.layout.ingredients_title_layout,
                new String[]{},
                new int[]{},
                presenter.getIngredients(recipeName),
                R.layout.ingredients_list_item,
                presenter.getKeysArray(),
                presenter.getViewsIdsArray() );

        ingredientsListView.setAdapter(adapter);
    }

    private void prepareStepsRecyclerView() {

        stepsRecyclerView.setAdapter(adapter);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter.setRecipeSteps(recipeName);
    }

}
