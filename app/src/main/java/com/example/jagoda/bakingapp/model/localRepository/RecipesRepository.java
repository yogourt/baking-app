package com.example.jagoda.bakingapp.model.localRepository;


import com.example.jagoda.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

//TODO: Implement Recipes Repository
public class RecipesRepository {

    public static List<String> getRecipesNames() {
        RealmResults<Recipe> recipes = Realm.getDefaultInstance().where(Recipe.class).findAll();

        List<String> recipesNames = new ArrayList<>();
        for(Recipe recipe: recipes) {
            recipesNames.add(recipe.getName());
        }
        return recipesNames;
    }

    public static void getRecipeIngredients() {

    }

    public static void getRecipeSteps() {

    }

    public static void getStepDetails() {

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
