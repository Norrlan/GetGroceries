package com.example.getgroceries;

// Adapter responsible for displaying all user-created lists on the Lists screen.
// Each row shows the list name, item count, and a delete button.

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

    // Adapter constructor
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
        // Bind list name and item count.

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

        //mdthod to udate the adapter with new data and refresh the UI.
    public void updateLists(List<UserList> newLists)
    {
        this.lists = newLists;
        notifyDataSetChanged();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder
    {

        // ViewHolder stores references to the views inside each row.
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