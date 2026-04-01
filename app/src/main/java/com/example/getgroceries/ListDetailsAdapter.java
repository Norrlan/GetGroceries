package com.example.getgroceries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListDetailsAdapter extends RecyclerView.Adapter<ListDetailsAdapter.ItemViewHolder> {

    private List<ListItem> items;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDelete(ListItem item);
    }

    public ListDetailsAdapter(List<ListItem> items, OnDeleteClickListener deleteListener) {
        this.items = items;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {
        ListItem item = items.get(position);

        holder.name.setText(item.getProductName());
        holder.price.setText("£" + item.getProductPrice());

        Glide.with(holder.image.getContext()).load(item.getProductImageUrl()).placeholder(R.drawable.condiment).into(holder.image);

        holder.delete.setOnClickListener(v -> deleteListener.onDelete(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView image, delete;
        TextView name, price;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.item_image);
            delete = itemView.findViewById(R.id.item_delete);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
        }
    }
}