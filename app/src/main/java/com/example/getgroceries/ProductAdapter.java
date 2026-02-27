package com.example.getgroceries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{
    private List<ProductModel> products;
    public ProductAdapter(List<ProductModel> products)
    {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position)
    {
        ProductModel p = products.get(position);
        holder.name.setText(p.name);
        holder.image.setImageResource(p.imageId);
        holder.saveButton.setOnClickListener(v ->{

        });
    }



    @Override
    public int getItemCount()
    {
        return products.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name, detail;
        Button saveButton;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            detail = itemView.findViewById(R.id.product_detail);
            saveButton = itemView.findViewById(R.id.save_button);
        }
    }

}
