package com.example.jagoda.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.model.Ingredient;
import com.example.jagoda.bakingapp.model.localRepository.RecipeRepository;

import java.util.List;

import io.realm.Realm;
import timber.log.Timber;

import static com.example.jagoda.bakingapp.presenter.StepListPresenter.SHARED_PREFERENCES_NAME;


public class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String KEY_RECIPE_NAME = "recipe_name";
    private List<Ingredient> ingredientsList;
    private String recipeName;
    private Context context;

    public ListRemoteViewFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        Timber.d("onCreate");
    }

    //called onStart and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {

        Timber.d("onDataSetChanged");

        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        recipeName = preferences.getString(KEY_RECIPE_NAME, "");
        ingredientsList = RecipeRepository.getIngredientsForWidget(recipeName);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(ingredientsList == null) return 0;
        else return ingredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if(ingredientsList == null || ingredientsList.isEmpty()) return null;
        Ingredient ingredient = ingredientsList.get(position);

        String quantity = String.valueOf(ingredient.getQuantity());
        String measure = ingredient.getMeasure();
        String ingredientName = ingredient.getIngredient();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_list_item);
        views.setTextViewText(R.id.quantity_text_view, quantity);
        views.setTextViewText(R.id.measure_text_view, measure);
        views.setTextViewText(R.id.ingredient_text_view, ingredientName);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
