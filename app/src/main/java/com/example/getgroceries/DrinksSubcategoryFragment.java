package com.example.getgroceries;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public class DrinksSubcategoryFragment extends Fragment {

    public DrinksSubcategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_drinks_subcategory, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.subcategory_recycler);

        List<SubCategoryModel> subcategories = Arrays.asList(
                new SubCategoryModel("Energy Drinks", R.drawable.redbull),
                new SubCategoryModel("Tea, Coffee & Beverages", R.drawable.cocoadrinks),
                new SubCategoryModel("Beer, Wine and Spirits", R.drawable.beerpacket),
                new SubCategoryModel("Soft Drinks", R.drawable.cokeimg),
                new SubCategoryModel("Fruit Juice", R.drawable.fruitjuice),
                new SubCategoryModel("Squash", R.drawable.squash)
        );

        SubcategoryAdapter adapter = new SubcategoryAdapter(subcategories, position -> {
            openSubcategory(position);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
    private void openSubcategory(int position) {
        // TODO: Navigate to the product list for this subcategory
        // Example:
        // Fragment fragment = new DrinksProductsFragment();
        // requireActivity().getSupportFragmentManager()
        //         .beginTransaction()
        //         .replace(R.id.fragment_container, fragment)
        //         .addToBackStack(null)
        //         .commit();
    }
}