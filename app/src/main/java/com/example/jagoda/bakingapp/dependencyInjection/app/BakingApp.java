package com.example.jagoda.bakingapp.dependencyInjection.app;

import android.app.Activity;
import android.app.Application;


public class BakingApp extends Application {

    BakingAppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerBakingAppComponent.create();

    }

    public BakingAppComponent getComponent() {
        return component;
    }

    public static BakingApp get(Activity activity){
        return (BakingApp) activity.getApplication();
    }
}
