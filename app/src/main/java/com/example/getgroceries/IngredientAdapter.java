package com.example.getgroceries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<IngredientModel> ingredients;

    public IngredientAdapter(List<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientModel ing = ingredients.get(position);

        holder.quantity.setText(ing.quantity);// cannot find symbol variable
        holder.name.setText(ing.name);// cannotfind
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView quantity, name;
        ImageView image;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.ingredient_checkbox);
            quantity = itemView.findViewById(R.id.Amount);
            name = itemView.findViewById(R.id.IngredientName);
            //image = itemView.findViewById(R.id.imgIngredient);
        }
    }
}