package com.example.jagoda.bakingapp.dependencyInjection.app;

import dagger.Component;

@BakingAppScope
@Component(modules = BakingAppModule.class)
public interface BakingAppComponent {

}
