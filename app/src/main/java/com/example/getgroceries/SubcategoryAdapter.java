package com.example.getgroceries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.ViewHolder> {

    private List<SubCategoryModel> subcategories;
    private OnSubcategoryClickListener listener;

    public interface OnSubcategoryClickListener {
        void onSubcategoryClick(int position);
    }

    public SubcategoryAdapter(List<SubCategoryModel> subcategories, OnSubcategoryClickListener listener) {
        this.subcategories = subcategories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategoryModel s = subcategories.get(position);

        holder.image.setImageResource(s.imageId);
        holder.name.setText(s.name);

        holder.itemView.setOnClickListener(v -> listener.onSubcategoryClick(position));
    }

    @Override
    public int getItemCount() {
        return subcategories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.subcategory_image);
            name = itemView.findViewById(R.id.subcategory_name);
        }
    }
}