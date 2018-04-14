package com.example.jagoda.bakingapp.dependencyInjection.app;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * This is application scope that is used to make singletons in dependency injection.
 */

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface BakingAppScope {
}
