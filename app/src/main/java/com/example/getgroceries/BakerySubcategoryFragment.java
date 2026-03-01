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

public class BakerySubcategoryFragment extends Fragment
{
    private List<SubCategoryModel> subcategories;
    public BakerySubcategoryFragment()
    {
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

        View view = inflater.inflate(R.layout.fragment_bakery_subcategory, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.subcategory_recycler3);
// These are the subcategories that will be used under the category for each store
        subcategories = Arrays.asList(
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
    private void openSubcategory(int position)
    {
        String title = subcategories.get(position).name;


        ProductListFragment fragment = ProductListFragment.newInstance(position, title);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}