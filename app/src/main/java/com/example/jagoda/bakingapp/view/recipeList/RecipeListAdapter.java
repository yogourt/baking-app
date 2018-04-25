package com.example.jagoda.bakingapp.view.recipeList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.model.Recipe;
import com.example.jagoda.bakingapp.view.recipeSteps.StepListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jagoda.bakingapp.view.recipeSteps.StepListFragment.KEY_RECIPE_NAME;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipesList;

    public RecipeListAdapter(Context context) {
        this.context = context;
    }


    public void setRecipesList(List<Recipe> recipesList) {
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
        holder.recipeName.setText(recipesList.get(position).getName());

        if(!recipesList.get(position).getImage().isEmpty()) {
            Uri imageUri = Uri.parse(recipesList.get(position).getImage());
            //Uri imageUri =
            Picasso.get()
                    .load(imageUri)
                    .into(holder.cakeImageView);

            holder.cakeIcon.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        if(recipesList == null)return 0;
        else return recipesList.size();
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_name_text_view)
        TextView recipeName;
        @BindView(R.id.cake_image_view)
        ImageView cakeImageView;
        @BindView(R.id.cake_icon)
        ImageView cakeIcon;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent stepsListIntent = new Intent(context, StepListActivity.class);
            stepsListIntent.putExtra(KEY_RECIPE_NAME, recipesList.get(getAdapterPosition()).getName());
            context.startActivity(stepsListIntent);
        }
    }

}
