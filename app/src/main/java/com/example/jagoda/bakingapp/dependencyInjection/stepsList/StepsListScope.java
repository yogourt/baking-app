package com.example.jagoda.bakingapp.dependencyInjection.stepsList;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface StepsListScope {
}
