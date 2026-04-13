package com.example.getgroceries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListViewHolder>
{

    private List<UserList> lists;
    private OnListClickListener listener;
    private OnDeleteClickListener deleteListener;

    public interface OnListClickListener
    {
        void onListClick(UserList list);
    }

    public interface OnDeleteClickListener
    {
        void onDeleteClick(UserList list);
    }

    public ListsAdapter(List<UserList> lists, OnListClickListener listener, OnDeleteClickListener deleteListener)
    {
        this.lists = lists;
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position)
    {
        UserList list = lists.get(position);

        holder.listName.setText(list.getName());
        holder.listCount.setText(list.getItems().size() + " items");

        holder.itemView.setOnClickListener(v -> listener.onListClick(list));
        holder.deleteBtn.setOnClickListener(v -> deleteListener.onDeleteClick(list));
    }

    @Override
    public int getItemCount()
    {
        return lists.size();
    }

    public void updateLists(List<UserList> newLists)
    {
        this.lists = newLists;
        notifyDataSetChanged();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder
    {

        TextView listName, listCount;
        ImageView deleteBtn;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.list_name);
            listCount = itemView.findViewById(R.id.list_count);
            deleteBtn = itemView.findViewById(R.id.listdeletebtn);
        }
    }
}