package com.example.getgroceries;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{
    private List<ProductModel> products;
    private FragmentActivity activity;

    public ProductAdapter(List<ProductModel> products, FragmentActivity activity)
    {
        this.products = products;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position)
    {
        ProductModel p = products.get(position);

        holder.name.setText(p.name);
        holder.detail.setText(p.detail);
        holder.image.setImageResource(p.imageId);

        holder.saveButton.setOnClickListener(v -> {

            Context context = v.getContext();

            // Get ViewModel from Activity
            ListsView listsView = new ViewModelProvider(activity)
                    .get(ListsView.class);


            // Build the ListItem from the product
            ListItem item = new ListItem(
                    p.name,
                    p.imageId,
                    0.0   // Replace with real price later
            );

            // Ask user: new list or existing list?
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Save to List")
                    .setMessage("Do you want to create a new list or add to an existing one?")
                    .setPositiveButton("New List", (dialog, which) -> {

                        // S Create new list dialog
                        EditText input = new EditText(context);
                        input.setHint("List name");

                        new AlertDialog.Builder(context)
                                .setTitle("Create New List")
                                .setView(input)
                                .setPositiveButton("Create", (d, w) -> {
                                    String listName = input.getText().toString().trim();
                                    if (!listName.isEmpty()) {
                                        listsView.createList(listName, item);
                                        showConfirmation(context);
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();

                    })
                    .setNegativeButton("Existing List", (dialog, which) -> {

                        // Choose existing list
                        List<UserList> allLists = listsView.getLists().getValue();

                        if (allLists == null || allLists.isEmpty()) {
                            Toast.makeText(context,
                                    "No lists available. Create one first.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String[] listNames = new String[allLists.size()];
                        for (int i = 0; i < allLists.size(); i++) {
                            listNames[i] = allLists.get(i).getName();
                        }

                        new AlertDialog.Builder(context)
                                .setTitle("Choose a List")
                                .setItems(listNames, (d, index) -> {
                                    UserList selected = allLists.get(index);
                                    listsView.addItemToList(selected.getId(), item);
                                    showConfirmation(context);
                                })
                                .show();

                    })
                    .show();
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
            detail = itemView.findViewById(R.id.product_info);
            saveButton = itemView.findViewById(R.id.save_button);
        }
    }

    // Confirmation message for saving items to list:
    private void showConfirmation(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Saved")
                .setMessage("Item saved to your list.")
                .setPositiveButton("OK", null)
                .show();
    }
}