package com.example.jagoda.bakingapp.dependencyInjection.stepList;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface StepListScope {
}
