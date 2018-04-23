package com.example.jagoda.bakingapp.model.localRepository;

import com.example.jagoda.bakingapp.model.Ingredient;
import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RecipesRepository {

    public static List<Recipe> getRecipes() {
        return Realm.getDefaultInstance().where(Recipe.class).findAll();
    }

    public static ArrayList<Ingredient> getRecipeIngredients(String recipeName) {
        Recipe recipe = Realm.getDefaultInstance().where(Recipe.class)
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
        Recipe recipe = Realm.getDefaultInstance().where(Recipe.class)
                .equalTo("name", recipeName).findFirst();

        if(recipe != null) {
            RealmList<Step> steps = recipe.getSteps();

            ArrayList<Step> stepsList = new ArrayList<>();
            stepsList.addAll(steps);
            return stepsList;
        }
        else return null;
    }

    public static Step getStep(String recipeName, int stepNumber) {
        Recipe recipe = Realm.getDefaultInstance().where(Recipe.class)
                .equalTo("name", recipeName).findFirst();

        if(recipe != null) {
            RealmList<Step> steps = recipe.getSteps();
            return steps.get(stepNumber - 1);
        }
        else return null;
    }

    public static void saveRecipes(final List<Recipe> recipesList) {

        Realm realm = Realm.getDefaultInstance();

        //add all recipes to database
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(recipesList);
                }
            });
    }

}
