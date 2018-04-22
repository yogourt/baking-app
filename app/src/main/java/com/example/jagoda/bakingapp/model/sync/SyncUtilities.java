package com.example.jagoda.bakingapp.model.sync;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.jagoda.bakingapp.dependencyInjection.app.BakingApp;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 *
 */
public class SyncUtilities {

    //sync with network should occure 1 time per day
    private static final int INTERVAL_SEC = (int) TimeUnit.DAYS.toSeconds(1);
    private static final int FLEXTIME_SEC = (int) TimeUnit.HOURS.toSeconds(1);

    private static final String SYNC_JOB_TAG = "baking_app_sync_tag";
    private static final String IMMEDIATE_SYNC_JOB_TAG = "baking_app_immediate_sync_tag";


    private static boolean sInitialized;

    synchronized public static void scheduleSync(@NonNull Context context) {

        if(sInitialized) return;

        GooglePlayDriver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job scheduledSyncJob = firebaseJobDispatcher.newJobBuilder()
                .setTag(SYNC_JOB_TAG)
                .setService(SyncJobService.class)
                .addConstraint(Constraint.ON_ANY_NETWORK)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.executionWindow(INTERVAL_SEC, INTERVAL_SEC + FLEXTIME_SEC))
                .build();

        firebaseJobDispatcher.schedule(scheduledSyncJob);
        Timber.d("regular sync planned");

        sInitialized = true;
    }

}
