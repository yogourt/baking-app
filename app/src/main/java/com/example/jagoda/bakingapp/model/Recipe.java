package com.example.jagoda.bakingapp.model;


import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private List<Ingredient> ingredients;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return  ingredients;
    }

    public ArrayList<String> getIngredientsStringList() {
        ArrayList<String> ingredientsStringList = new ArrayList<>();
        for(Ingredient ingredient: ingredients) {
            ingredientsStringList.add(ingredient.getIngredientString());
        }
        return ingredientsStringList;
    }

    class Ingredient {

        private float quantity;
        private String measure;
        private String ingredient;

        public float getQuantity() {
            return quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public String getIngredient() {
            return ingredient;
        }

        public String getIngredientString() {
            return quantity + " " + measure + " " + ingredient;
        }

    }
}
