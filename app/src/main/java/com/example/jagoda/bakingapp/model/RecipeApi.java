package com.example.jagoda.bakingapp.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * This is Api declaration for Retrofit's network request.
 */

public interface RecipeApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipesList();
}
