package com.example.getgroceries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedRecipeAdapter extends RecyclerView.Adapter<SavedRecipeAdapter.ViewHolder>
{

    private List<SavedRecipe> recipeList;
    private OnRecipeClickListener listener;

    public interface OnRecipeClickListener
    {
        void onRecipeClick(SavedRecipe recipe);
        void onDeleteClick(SavedRecipe recipe);
    }

    public SavedRecipeAdapter(List<SavedRecipe> recipeList, OnRecipeClickListener listener)
    {
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_recipe_item, parent, false); // Make sure XML name matches
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        SavedRecipe recipe = recipeList.get(position);

        holder.title.setText(recipe.title);
        // Show first ingredient as preview
        if (recipe.ingredients != null && recipe.ingredients.contains("\n"))
        {
            holder.preview.setText(recipe.ingredients.split("\n")[0]);
        }
        else
        {
            holder.preview.setText(recipe.ingredients);
        }

        // Open recipe
        holder.itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));

        // Delete recipe
        holder.deleteBtn.setOnClickListener(v -> listener.onDeleteClick(recipe));
    }

    @Override
    public int getItemCount()
    {
        return recipeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title, preview;
        ImageView deleteBtn;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            preview = itemView.findViewById(R.id.item_preview);
            deleteBtn = itemView.findViewById(R.id.btnDelete);
        }
    }
}