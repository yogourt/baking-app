package com.example.jagoda.bakingapp.presenter;


import android.util.ArrayMap;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.model.Ingredient;
import com.example.jagoda.bakingapp.model.localRepository.RecipesRepository;
import com.example.jagoda.bakingapp.view.recipeSteps.StepsListActivity;
import com.example.jagoda.bakingapp.view.recipeSteps.StepsListAdapter;
import com.example.jagoda.bakingapp.view.recipesList.RecipesListActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class StepsListPresenter {

    private static final String KEY_INGREDIENTS_TITLE = "ingredients_title";
    private static final String KEY_INGREDIENT_QUANTITY = "ingredient_quantity";
    private static final String KEY_INGREDIENT_MEASURE = "ingredient_measure";
    private static final String KEY_INGREDIENT = "ingredient";

    @Inject
    StepsListActivity activity;

    @Inject
    StepsListAdapter adapter;

    public ArrayList<ArrayList<ArrayMap<String, String>>> getIngredients(String recipeName) {
        ArrayList<Ingredient> ingredients = RecipesRepository.getRecipeIngredients(recipeName);

        if(ingredients == null) return null;

        ArrayList<ArrayMap<String, String>> ingredientsArrayList = new ArrayList<>();

        for(Ingredient ingredient: ingredients) {
            ArrayMap<String, String> ingredientArrayMap = new ArrayMap<>();
            ingredientArrayMap.put(KEY_INGREDIENT_QUANTITY, String.valueOf(ingredient.getQuantity()));
            ingredientArrayMap.put(KEY_INGREDIENT_MEASURE, ingredient.getMeasure());
            ingredientArrayMap.put(KEY_INGREDIENT, ingredient.getIngredient());
            ingredientsArrayList.add(ingredientArrayMap);
        }
        //outer array list is not needed in this case as there only be one expandable group in list view,
        //but it is needed by constructor of SimpleExpandableListAdapter
        ArrayList<ArrayList<ArrayMap<String, String>>> ingredientsOuterArrayList = new ArrayList<>();
        ingredientsOuterArrayList.add(ingredientsArrayList);

        return ingredientsOuterArrayList;
    }

    public String[] getKeysArray() {
        return new String[]{KEY_INGREDIENT_QUANTITY, KEY_INGREDIENT_MEASURE, KEY_INGREDIENT};
    }

    public int[] getViewsIdsArray() {
        return new int[]{R.id.quantity_text_view, R.id.measure_text_view, R.id.ingredient_text_view};
    }
    public void setRecipeSteps(String recipeName) {
        adapter.setStepsList(RecipesRepository.getRecipeSteps(recipeName));
    }

    public List<ArrayMap<String, String>> getIngredientsTitle() {
        ArrayList<ArrayMap<String, String>> titleArrayList = new ArrayList<>();
        ArrayMap<String, String> titleArrayMap = new ArrayMap<>();
        titleArrayMap.put(KEY_INGREDIENTS_TITLE, activity.getString(R.string.ingredients_label));
        titleArrayList.add(titleArrayMap);
        return titleArrayList;
    }

}
