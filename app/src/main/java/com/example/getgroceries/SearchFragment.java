package com.example.getgroceries;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchFragment extends Fragment
{
    //initialise the filters
    private String selectedStore = null;
    private String selectedSort = null;
    private String selectedCategory = null;
    private int minPrice = 0;
    private int maxPrice = 999;
    private String currentQuery = "";
    private List<GroceryProducts> allProducts = new ArrayList<>();
    private SearchAdapterResults resultsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // inflate the layout
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView searchResultsRecycler = view.findViewById(R.id.search_results_recycler);
        resultsAdapter = new SearchAdapterResults(new ArrayList<>()); //Initialise the adapter
        searchResultsRecycler.setAdapter(resultsAdapter);   // Assign adapter to RecyclerView
        // load the linear  layout to the search results recycler view
        searchResultsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        loadProductsFromFirestore();

        //Search bar logic
        TextInputEditText searchBar = view.findViewById(R.id.Search_bar);
        searchBar.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count)
            {
                currentQuery = text.toString().trim();
                // Re-apply filters
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // The filters
        Chip chipSort = view.findViewById(R.id.chipSort);
        Chip chipStores = view.findViewById(R.id.chipStores);
        Chip chipSize = view.findViewById(R.id.chipSize);
        Chip chipQuantity = view.findViewById(R.id.chipQuantity);

        // Display dialog box when the sort filter chip is clicked
        chipSort.setOnClickListener(v ->
        {
            selectedSort = "price_low_high";   // Assign sort mode
            applyFilters();
        });

        // Display a Alert Dialog with MultipleItemSelection when the store filter chip is clicked
        chipStores.setOnClickListener(v ->
        {
            String[] superstores = {"Asda", "Ebay", "Amazon", "Walmart", "Tesco"};
            boolean[] checkedItems = new boolean[superstores.length];

            new AlertDialog.Builder(getContext())
                    .setTitle("Select Superstore")
                    .setMultiChoiceItems(superstores, checkedItems, (dialog, which, isChecked) ->
                    {
                        checkedItems[which] = isChecked;
                    })
                    .setPositiveButton("Done", (dialog, which) ->
                    {
                        List<String> selected = new ArrayList<>();
                        for (int i = 0; i < checkedItems.length; i++)
                        {
                            if (checkedItems[i])
                            {
                                selected.add(superstores[i]);
                            }
                        }

                        if (selected.isEmpty())
                        {
                            selectedStore = null;
                            chipStores.setText("Stores");
                        }
                        else
                        {
                            selectedStore = selected.get(0);
                            chipStores.setText(selectedStore);
                        }

                        applyFilters();
                        // cancel and clear all buttons in the dialog
                    })
                    .setNegativeButton("Cancel", null)
                    .setNeutralButton("Clear All", (dialog, which) ->
                    {
                        Arrays.fill(checkedItems, false);
                        selectedStore = null;
                        chipStores.setText("Stores");
                        applyFilters();
                    })
                    .show();
        });

        // Display dialog box when the category filter chips is clicked
        chipSize.setOnClickListener(v ->
        {
            String[] categories = {"Grocery", "Bakery", "Chilled Food", "Books","Fashion","Electronic","Meat & Poultry", "Makeup","Baby", "Drinks"};

            new AlertDialog.Builder(getContext())
                    .setTitle("Select Category")
                    .setItems(categories, (dialog1, which1) ->
                    {
                        selectedCategory = categories[which1];
                        chipSize.setText(selectedCategory);
                        applyFilters();
                    })
                    .show();
        });

        chipQuantity.setOnClickListener(v ->
        {
            String[] priceOptions = {"Lowest to Highest", "Highest to Lowest"};

            new AlertDialog.Builder(getContext())
                    .setTitle("Sort by Price")
                    .setItems(priceOptions, (dialog, which) ->
                    {
                        if (which == 0)
                        {
                            selectedSort = "price_low_high";
                            chipQuantity.setText("£ Low → High");
                        }
                        else
                        {
                            selectedSort = "price_high_low";
                            chipQuantity.setText("£ High → Low");
                        }

                        applyFilters();
                    })
                    .show();
        });

        return view;
    }

    //Load the products field from the  products collection. Use it search filtering.
    private void loadProductsFromFirestore()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").get().addOnSuccessListener(querySnapshot ->
        {
            allProducts.clear(); // Reset list
            for (DocumentSnapshot doc : querySnapshot)
            {
                // Convert Firestore document into GroceryProducts object
                GroceryProducts product = doc.toObject(GroceryProducts.class);

                // Add object to local  list
                allProducts.add(product);
            }

            applyFilters();
        });
    }

    //method to apply search filters
    private void applyFilters()
    {
        List<GroceryProducts> filtered = new ArrayList<>();

        for (GroceryProducts p : allProducts)
        {
            if (!currentQuery.isEmpty())
            {
                if (!p.getNamefield().toLowerCase().contains(currentQuery.toLowerCase()))
                {
                    continue;
                }
            }

            if (selectedStore != null && !p.getStoreName().equalsIgnoreCase(selectedStore))
                continue;
            if (selectedCategory != null && !p.getProductcategory().equalsIgnoreCase(selectedCategory))
                continue;

            double price = Double.parseDouble(p.getPricefield());// price filter
            if (price < minPrice || price > maxPrice)
                continue;

            //add the products to the list after filtering
            filtered.add(p);
        }

        // fetch the data if the user clicks the sort chip for High to low
        if ("price_high_low".equals(selectedSort))
        {
            filtered.sort(Comparator.comparingDouble(item -> Double.parseDouble(item.getPricefield())));
        }

        // fetch the data if the user clicks the sort chip for low_high
        if ("price_low_high".equals(selectedSort))
        {
            filtered.sort((a, b) ->
                    Double.compare(Double.parseDouble(a.getPricefield()),
                            Double.parseDouble(b.getPricefield())));
        }

        resultsAdapter.updateResults(filtered);
    }
}