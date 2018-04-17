package com.example.jagoda.bakingapp.model;


import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Recipe extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private RealmList<Ingredient> ingredients;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RealmList<Ingredient> getIngredients() {
        return  ingredients;
    }

    public ArrayList<String> getIngredientsStringList() {
        ArrayList<String> ingredientsStringList = new ArrayList<>();
        for(Ingredient ingredient: ingredients) {
            ingredientsStringList.add(ingredient.getIngredientString());
        }
        return ingredientsStringList;
    }

}
