package com.example.jagoda.bakingapp.widget;


import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.jagoda.bakingapp.R;

public class UpdateWidgetService extends IntentService {

    private static final String REFRESH_ACTION = "com.example.jagoda.bakingapp.widget.action.REFRESH";

    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }

    public static void startActionUpdateWidgets(Context context) {
        Intent updateWidgetServiceIntent = new Intent(context, UpdateWidgetService.class);
        updateWidgetServiceIntent.setAction(REFRESH_ACTION);
        context.startService(updateWidgetServiceIntent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        switch(intent.getAction()) {
            case REFRESH_ACTION:
                updateWidgets();
        }
    }

    private void updateWidgets() {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        ComponentName componentName = new ComponentName(this, IngredientsWidgetProvider.class);
        int[] widgetIds = manager.getAppWidgetIds(componentName);
        manager.notifyAppWidgetViewDataChanged(widgetIds,
                R.id.ingredients_list_view_widget);
        IngredientsWidgetProvider.updateAppWidgets(this, manager, widgetIds);
    }
}
