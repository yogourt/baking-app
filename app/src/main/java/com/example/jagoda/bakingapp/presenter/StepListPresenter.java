package com.example.jagoda.bakingapp.presenter;


import android.content.Context;
import android.util.ArrayMap;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.model.Ingredient;
import com.example.jagoda.bakingapp.model.localRepository.RecipeRepository;
import com.example.jagoda.bakingapp.view.stepList.StepListActivity;
import com.example.jagoda.bakingapp.view.stepList.StepListAdapter;
import com.example.jagoda.bakingapp.widget.UpdateWidgetService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.jagoda.bakingapp.view.stepDetails.StepDetailsFragment.KEY_RECIPE_NAME;

public class StepListPresenter {

    private static final String KEY_INGREDIENTS_TITLE = "ingredients_title";
    private static final String KEY_INGREDIENT_QUANTITY = "ingredient_quantity";
    private static final String KEY_INGREDIENT_MEASURE = "ingredient_measure";
    private static final String KEY_INGREDIENT = "ingredient";

    public static final String SHARED_PREFERENCES_NAME = "fav_recipes";

    @Inject
    StepListActivity activity;

    @Inject
    StepListAdapter adapter;

    public ArrayList<ArrayList<ArrayMap<String, String>>> getIngredients(String recipeName) {
        ArrayList<Ingredient> ingredients = RecipeRepository.getRecipeIngredients(recipeName);

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
        adapter.setStepsList(RecipeRepository.getRecipeSteps(recipeName));
    }

    public List<ArrayMap<String, String>> getIngredientsTitle() {
        ArrayList<ArrayMap<String, String>> titleArrayList = new ArrayList<>();
        ArrayMap<String, String> titleArrayMap = new ArrayMap<>();
        titleArrayMap.put(KEY_INGREDIENTS_TITLE, activity.getString(R.string.ingredients_label));
        titleArrayList.add(titleArrayMap);
        return titleArrayList;
    }

    public void showInWidget() {

        String recipeName = activity.getIntent().getStringExtra(KEY_RECIPE_NAME);
        activity.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putString(KEY_RECIPE_NAME, recipeName).apply();
        UpdateWidgetService.startActionUpdateWidgets(activity);
    }

}
