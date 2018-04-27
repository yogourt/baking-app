package com.example.jagoda.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.view.stepList.StepListActivity;

import static com.example.jagoda.bakingapp.presenter.StepListPresenter.SHARED_PREFERENCES_NAME;
import static com.example.jagoda.bakingapp.widget.ListRemoteViewFactory.KEY_RECIPE_NAME;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.ingredient_list_widget);

        String recipeName = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE).getString(KEY_RECIPE_NAME, "");
        views.setTextViewText(R.id.recipe_name_text_view_widget, recipeName);

        Intent adapterIntent = new Intent(context, ListRemoteViewsService.class);
        views.setRemoteAdapter(R.id.ingredients_list_view_widget, adapterIntent);

        Intent stepListIntent = new Intent(context, StepListActivity.class);
        stepListIntent.putExtra(KEY_RECIPE_NAME, recipeName);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context,
                0, stepListIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.recipe_name_text_view_widget, appPendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //the service takes care of updating widgets
        UpdateWidgetService.startActionUpdateWidgets(context);
    }

    public static void updateAppWidgets(Context context, AppWidgetManager manager, int[] widgetIds) {
        for(int widgetId: widgetIds) {
            updateAppWidget(context, manager, widgetId);
        }

    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

