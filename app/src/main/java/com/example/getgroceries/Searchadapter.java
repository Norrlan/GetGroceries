package com.example.getgroceries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Searchadapter extends RecyclerView.Adapter<Searchadapter.ViewHolder>
{

    private List<Integer> images;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }

    public Searchadapter(List<Integer> images, OnCategoryClickListener listener) {
        this.images = images;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card_drinks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryImage.setImageResource(images.get(position));

        holder.itemView.setOnClickListener(v -> {
            listener.onCategoryClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_drinks_img);
        }
    }

}