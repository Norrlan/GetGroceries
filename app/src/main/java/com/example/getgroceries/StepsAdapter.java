package com.example.getgroceries;
// for displaying steps after they have been fetched in RecipeSteps.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    private List<RecipeSteps> steps;

    public StepsAdapter(List<RecipeSteps> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_steps, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        RecipeSteps step = steps.get(position);

        holder.number.setText(String.valueOf(step.number));
        holder.title.setText(step.title);
        holder.desc.setText(step.description);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView number, title, desc;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.tvStepNumber);
            title = itemView.findViewById(R.id.tvStepTitle);
            desc = itemView.findViewById(R.id.tvStepDesc);
        }
    }
}