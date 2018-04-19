package com.example.jagoda.bakingapp.view.recipeSteps;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jagoda.bakingapp.R;
import com.example.jagoda.bakingapp.model.sync.Step;

import java.util.List;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepViewHolder> {

    private Context context;

    private List<Step> stepsList;

    public void setStepsList(List<Step> stepsList) {
        this.stepsList = stepsList;
        notifyDataSetChanged();
    }

    public StepsListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.steps_list_item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.stepNumberTv.setText(String.valueOf(position + 1));
        holder.stepShortDescTv.setText(stepsList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(stepsList == null) return 0;
        else return stepsList.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView stepNumberTv;
        private TextView stepShortDescTv;

        public StepViewHolder(View itemView) {
            super(itemView);

            stepNumberTv = itemView.findViewById(R.id.step_number_text_view);
            stepShortDescTv = itemView.findViewById(R.id.step_short_desc_text_view);
        }

        @Override
        public void onClick(View view) {
            Intent stepDetailsIntent = new Intent();
        }
    }
}
