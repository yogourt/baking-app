package com.example.jagoda.bakingapp.dependencyInjection.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import com.example.jagoda.bakingapp.model.sync.SyncUtilities;


public class BakingApp extends Application {

    BakingAppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerBakingAppComponent.create();

        //schedule SQLite sync with network source to happen once a day
        SyncUtilities.scheduleSync(this);

    }

    public BakingAppComponent getComponent() {
        return component;
    }

    public static BakingApp get(Activity activity){
        return (BakingApp) activity.getApplication();
    }

    public static BakingApp get(Service service) {
        return (BakingApp) service.getApplication();
    }

}
