package com.example.jagoda.bakingapp.model.localRepository;


import android.widget.ArrayAdapter;

import com.example.jagoda.bakingapp.model.Ingredient;
import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.model.sync.Step;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

//TODO: Implement Recipes Repository
public class RecipesRepository {

    private static  RealmConfiguration config = new RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build();

    public static List<String> getRecipesNames() {
        RealmResults<Recipe> recipes = Realm.getInstance(config).where(Recipe.class).findAll();

        List<String> recipesNames = new ArrayList<>();
        for(Recipe recipe: recipes) {
            recipesNames.add(recipe.getName());
        }
        return recipesNames;
    }

    public static ArrayList<Ingredient> getRecipeIngredients(String recipeName) {
        Recipe recipe = Realm.getInstance(config).where(Recipe.class)
                .equalTo("name", recipeName).findFirst();

        if(recipe != null) {
            RealmList<Ingredient> ingredients = recipe.getIngredients();

            ArrayList<Ingredient> ingredientsList = new ArrayList<>();
            ingredientsList.addAll(ingredients);
            return ingredientsList;
        }
        else return null;
    }

    public static ArrayList<Step> getRecipeSteps(String recipeName) {
        Recipe recipe = Realm.getInstance(config).where(Recipe.class)
                .equalTo("name", recipeName).findFirst();

        if(recipe != null) {
            RealmList<Step> steps = recipe.getSteps();

            ArrayList<Step> stepsList = new ArrayList<>();
            stepsList.addAll(steps);
            return stepsList;
        }
        else return null;
    }

    public static void getStepDetails() {

    }

    public static void saveRecipes(final List<Recipe> recipesList) {

        Realm realm = Realm.getInstance(config);

        //add all recipes to database
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(recipesList);
                }
            });
    }

}
