package com.example.jagoda.bakingapp.dependencyInjection.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Intent;

import com.example.jagoda.bakingapp.model.sync.SyncJobService;
import com.example.jagoda.bakingapp.model.sync.SyncUtilities;

import io.realm.Realm;


public class BakingApp extends Application {

    BakingAppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerBakingAppComponent.create();


        //init Realm database, it should be done once when app starts
        Realm.init(this);

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
