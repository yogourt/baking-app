package com.example.jagoda.bakingapp.dependencyInjection.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.support.v4.app.Fragment;

import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.model.RecipesApi;
import com.example.jagoda.bakingapp.model.localRepository.RecipesRepository;
import com.example.jagoda.bakingapp.model.sync.SyncUtilities;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class BakingApp extends Application {

    @Inject
    RecipesApi recipesApi;

    BakingAppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerBakingAppComponent.create();
        //init Realm database, it should be done once when app starts
        Realm.init(this);

        Timber.plant(new Timber.DebugTree());

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

    public static BakingApp get(Fragment fragment) {
        return (BakingApp) fragment.getActivity().getApplication();
    }

}
