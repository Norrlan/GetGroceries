package com.example.getgroceries;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import android.widget.Toast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{
    private List<GroceryProducts> products;
    private FragmentActivity activity;

    public ProductAdapter(List<GroceryProducts> products, FragmentActivity activity)
    {
        this.products = products;
        this.activity = activity;
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
        GroceryProducts p = products.get(position);

        holder.name.setText(p.getNamefield());
        holder.detail.setText(p.getProductcategory()); // the category
        holder.price.setText(p.getCurrencyfield()+p.getPricefield());
        //open the link in a browser when user taps on it.
        holder.link.setOnClickListener(v ->
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(p.getStorelink()));
            v.getContext().startActivity(intent);
        });
        //Glide library to load the images from the Url. and the store logo
        Glide.with(holder.image.getContext()).load(p.getImageUrl()).placeholder(R.drawable.condiment).into(holder.image);
        // Glide.with(holder.store.getContext()).load(p.getStoreLogo()).placeholder(R.drawable.amazon_logo).into(holder.store);
        holder.store.setImageResource(p.getStoreLogo()); // method call from GroceryProducts.java


        //save button save the items to a List on click
        holder.saveButton.setOnClickListener(v ->
        {
            Context context = v.getContext();
            // Get ViewModel from Activity
            ListsView listsView = new ViewModelProvider(activity).get(ListsView.class);

            //  Each list is filled with products that follow the ListItem Constructor
            ListItem item = new ListItem(p);

            // Ask the user new list or existing list?
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Save to List").setMessage("Create a new list or add to an existing one?").setPositiveButton("New List", (dialog, which) ->
                    {
                        // Create new list dialog
                        EditText input = new EditText(context);
                        input.setHint("List name");

                        new AlertDialog.Builder(context)
                                .setTitle("Create New List").setView(input)
                                .setPositiveButton("Create", (d, w) ->
                                {
                                    String listName = input.getText().toString().trim();
                                    if (!listName.isEmpty())
                                    {
                                        listsView.createList(listName, item);
                                        showConfirmation(context);
                                    }
                                })
                                .setNegativeButton("Cancel", null).show();

                    })
                    .setNegativeButton("Existing List", (dialog, which) ->
                    {
                        // Choose existing list
                        List<UserList> allLists = listsView.getLists().getValue();

                        if (allLists == null || allLists.isEmpty())
                        {
                            Toast.makeText(context, "No lists available. Create one first.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String[] listNames = new String[allLists.size()];
                        for (int i = 0; i < allLists.size(); i++)
                        {
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

    //method to get the number of products/items stored in a  single grocery list
    @Override
    public int getItemCount()
    {
        return products.size();
    }

    //
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView price, name, detail;
        ImageView image, store, link;
        Button saveButton;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            link = itemView.findViewById(R.id.external_link);
            store = itemView.findViewById(R.id.store_logo);
            detail = itemView.findViewById(R.id.product_category);
            saveButton = itemView.findViewById(R.id.save_button);
        }
    }

    // Confirmation message for saving items to list:
    private void showConfirmation(Context context)
    {
        new AlertDialog.Builder(context).setTitle("Saved").setMessage("Item saved to your list.").setPositiveButton("OK", null).show();
    }
}