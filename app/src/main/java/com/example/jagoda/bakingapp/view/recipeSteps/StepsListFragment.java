package com.example.jagoda.bakingapp.view.recipeSteps;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jagoda.bakingapp.R;

import java.util.ArrayList;

import static com.example.jagoda.bakingapp.view.recipesList.RecipesListAdapter.KEY_INGREDIENTS;

/**
 *
 */
public class StepsListFragment extends Fragment {


    private ListView ingredientsListView;

    public StepsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_list, container, false);

        ingredientsListView = view.findViewById(R.id.ingredients_list_view);


        Intent intent = getActivity().getIntent();
        if(intent != null) {
            ArrayList<String> ingredients = intent.getStringArrayListExtra(KEY_INGREDIENTS);
            prepareIngredients(ingredients);
        }

        return view;
    }

    private void prepareIngredients(ArrayList<String> ingredients) {
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.ingredients_list_item,
                R.id.ingredient_text_view, ingredients);
        ingredientsListView.setAdapter(adapter);
    }

}
