package com.example.jagoda.bakingapp.model;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Recipe extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private RealmList<Ingredient> ingredients;
    private RealmList<Step> steps;
    private int servings;
    private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RealmList<Ingredient> getIngredients() {
        return  ingredients;
    }

    public RealmList<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
