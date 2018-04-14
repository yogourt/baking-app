package com.example.jagoda.bakingapp.dependencyInjection.recipesList;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * This is the scope for recipes list activity
 */

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface RecipesListScope {
}
