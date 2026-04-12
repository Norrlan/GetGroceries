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
    private List<String> selectedStores = new ArrayList<>();
    private List<String> selectedCategories = new ArrayList<>();
    private String selectedSort = null;
    private int minPrice = 0;
    private int maxPrice = 999;
    private String currentQuery = "";
    private List<GroceryProducts> allProducts = new ArrayList<>();
    private SearchAdapterResults resultsAdapter;
    private RecyclerView searchResultsRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // inflate the layout
        View view = inflater.inflate(R.layout.fragment_search, container, false);
// setting up the recycler view in the xml file to load products from Firestore
        searchResultsRecycler = view.findViewById(R.id.search_results_recycler);
        resultsAdapter = new SearchAdapterResults(getContext(), new ArrayList<>());
        searchResultsRecycler.setAdapter(resultsAdapter);
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
        Chip chipStores = view.findViewById(R.id.chipStores);
        Chip chipCategory = view.findViewById(R.id.chip_category);
        Chip chipPrice =  view.findViewById(R.id.chip_prices);


        // Display a Alert Dialog with MultipleItemSelection when the store filter chip is clicked
        chipStores.setOnClickListener(v ->
        {

             final String[] superstores = {"Asda", "Ebay", "Amazon", "Walmart", "Tesco"}; // Store I will be using
            boolean[] checkedItems = new boolean[superstores.length];

            for (int i = 0; i < superstores.length; i++)  // loop through the array
            {
                checkedItems[i] = selectedStores.contains(superstores[i]);
            }

            new AlertDialog.Builder(getContext())
                    .setTitle("Select Superstore")
                    .setMultiChoiceItems(superstores, checkedItems, (dialog, which, isChecked) -> {
                        checkedItems[which] = isChecked;
                    })
                    .setPositiveButton("Done", (dialog, which) -> {

                        selectedStores.clear();
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {
                                selectedStores.add(superstores[i]);
                            }
                        }

                        if (selectedStores.isEmpty()) {
                            chipStores.setText("Stores");
                        } else {
                            chipStores.setText(String.join(", ", selectedStores));
                        }

                        applyFilters();
                    })
                    .setNegativeButton("Cancel", null)
                    .setNeutralButton("Clear All", (dialog, which) -> {
                        Arrays.fill(checkedItems, false);
                        selectedStores.clear();
                        chipStores.setText("Stores");
                        applyFilters();
                    })
                    .show();
        });

        // Display dialog box when the category filter chips is clicked
        //Reference GeeksforGeeks.com
        chipCategory.setOnClickListener(v -> {

            String[] categories = {"Grocery", "Bakery", "Chilled Food", "Books","Fashion","Electronic","Meat & Poultry", "Makeup","Baby", "Drinks"};
            boolean[] checkedItems = new boolean[categories.length];

            for (int i = 0; i < categories.length; i++)
            {
                checkedItems[i] = selectedCategories.contains(categories[i]);
            }

            new AlertDialog.Builder(getContext())
                    .setTitle("Select Category")
                    .setMultiChoiceItems(categories, checkedItems, (dialog, which, isChecked) ->
                    {
                        checkedItems[which] = isChecked;
                    })
                    .setPositiveButton("Done", (dialog, which) ->
                    {

                        selectedCategories.clear();
                        for (int i = 0; i < checkedItems.length; i++)
                        {
                            if (checkedItems[i])
                            {
                                selectedCategories.add(categories[i]);
                            }
                        }

                        if (selectedCategories.isEmpty())
                        {
                            chipCategory.setText("Category");
                        }
                    else
                        {
                            chipCategory.setText(String.join(", ", selectedCategories));
                        }

                        applyFilters();
                    })
                    .setNegativeButton("Cancel", null)
                    .setNeutralButton("Clear All", (dialog, which) ->
                    {
                        Arrays.fill(checkedItems, false);
                        selectedCategories.clear();
                        chipCategory.setText("Category");
                        applyFilters();
                    })
                    .show();
        });

        chipPrice.setOnClickListener(v ->
        {
            String[] priceOptions = {"Lowest to Highest", "Highest to Lowest"};

            // Dialog box logic to apply the filters under the price chip.
            new AlertDialog.Builder(getContext()).setTitle("Sort by Price").setItems(priceOptions, (dialog, which) ->
                    {
                        if (which == 0)
                        {
                            selectedSort = "price_low_high";
                            chipPrice.setText("£ Low → High");
                        }
                        else
                        {
                            selectedSort = "price_high_low";
                            chipPrice.setText("£ High → Low");
                        }

                        applyFilters();
                    })
                    .show();
        });

        return view;
    }

    //Load the products field from the  products collection. Use it in search filtering.
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
                if (product != null)
                    allProducts.add(product);
            }

            applyFilters();
        });
    }

    //method to apply search filters
    private void applyFilters()
    {
        // produce a new list of products when a filter is clicked
        List<GroceryProducts> filtered = new ArrayList<>();

        for (GroceryProducts p : allProducts)
        {
            if (p.getNamefield() == null ||
                p.getStoreName() == null ||
                p.getProductcategory() == null ||
                p.getPricefield() == null)
                continue;

            // enable the search query

            if (!currentQuery.isEmpty())
            {
                if (!p.getNamefield().toLowerCase().contains(currentQuery.toLowerCase()))
                {
                    continue;
                }
            }

            if (!selectedStores.isEmpty() && !selectedStores.contains(p.getStoreName()))
                continue;

            if (!selectedCategories.isEmpty() && !selectedCategories.contains(p.getProductcategory()))
                continue;

            // Price range
            double price;
            try { price = Double.parseDouble(p.getPricefield()); }
            catch (NumberFormatException e) { continue; }
            if (price < minPrice || price > maxPrice) continue;

            //add the products to the list after filtering
            filtered.add(p);
        }

        // Sort for the prices filter if low to high. compare 2 values and present the products from lowest to highest
        if ("price_low_high".equals(selectedSort))
        {
            filtered.sort((a, b) ->
            {
                try
                {
                    return Double.compare(Double.parseDouble(a.getPricefield()),
                        Double.parseDouble(b.getPricefield()));
                }
                catch (NumberFormatException e) { return 0; } // in case the number format is different e.g: int
            });
        }
        else if ("price_high_low".equals(selectedSort)) // same as price low high but in reverse.
        {
            filtered.sort((a, b) ->
            {
                try
                { return Double.compare(Double.parseDouble(b.getPricefield()),
                        Double.parseDouble(a.getPricefield()));
                }
                catch (NumberFormatException e) { return 0; }
            });
        }

        resultsAdapter.updateResults(filtered); // update  the adapter with the filtered results.
    }
}