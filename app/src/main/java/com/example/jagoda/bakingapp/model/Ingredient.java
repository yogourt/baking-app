package com.example.jagoda.bakingapp.model;

import io.realm.RealmObject;


public class Ingredient extends RealmObject {

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
