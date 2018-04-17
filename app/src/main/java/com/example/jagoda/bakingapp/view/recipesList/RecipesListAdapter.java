package com.example.jagoda.bakingapp.view.recipesList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.view.recipeSteps.StepsListActivity;

import java.util.List;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipeViewHolder> {

    private Context context;
    private List<String> recipesList;

    public RecipesListAdapter(Context context) {
        this.context = context;
    }


    public void setRecipesList(List<String> recipesList) {
        this.recipesList = recipesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recipes_list_item, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipeName.setText(recipesList.get(position));
    }

    @Override
    public int getItemCount() {
        if(recipesList == null)return 0;
        else return recipesList.size();
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView recipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent stepsListIntent = new Intent(context, StepsListActivity.class);
            context.startActivity(stepsListIntent);
        }
    }

}
