package com.example.getgroceries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchAdapterResults extends RecyclerView.Adapter<SearchAdapterResults.ViewHolder>
{

    private List<GroceryProducts> results;
    private Context context;

    public SearchAdapterResults(List<GroceryProducts> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.search_result_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        GroceryProducts product = results.get(position);

        holder.name.setText(product.getNamefield());
        holder.price.setText("£" + product.getPricefield());
        holder.store.setText(product.getStoreName());

        Glide.with(context)
                .load(product.getImageUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void updateResults(List<GroceryProducts> newResults)
    {
        this.results = newResults;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        TextView name, price, store;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            image = itemView.findViewById(R.id.search_item_image);
            name = itemView.findViewById(R.id.search_item_name);
            price = itemView.findViewById(R.id.search_item_price);
            store = itemView.findViewById(R.id.search_item_store);
        }
    }
}