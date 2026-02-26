package com.example.getgroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private List<String> allProducts = Arrays.asList(
            "Bakery",
            "Drinks",
            "Frozen",
            "Household",
            "Snacks",
            "Apple",
            "Orange",
            "Milk",
            "Bread",
            "Coke",
            "Chicken",
            "Rice",
            "Eggs",
            "Butter"
    );

    private void openCategory(int position) {
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new DrinksFragment();
                break;
            case 1:
                fragment = new SnacksFragment();
                break;
            case 2:
                fragment = new BakeryFragment();
                break;
            case 3:
                fragment = new FrozenFragment();
                break;
            case 4:
                fragment = new CannedFoodFragment();
                break;
            case 5:
                fragment = new CondimentsFragment();
                break;
            default:
                return;
        }

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Category Cards
        RecyclerView categoryRecycler = view.findViewById(R.id.category_recycler);

        List<Integer> categoryImages = Arrays.asList(
                R.drawable.drinks_card,
                R.drawable.snacks,
                R.drawable.bakery,
                R.drawable.frozenfood,
                R.drawable.canfood,
                R.drawable.condiment
        );

        Searchadapter adapter = new Searchadapter(categoryImages, position -> {
            openCategory(position);
        });

        categoryRecycler.setAdapter(adapter);
        categoryRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // SEARCH RESULTS RECYCLER
        RecyclerView searchResultsRecycler = view.findViewById(R.id.search_results_recycler);
        SearchResultsAdapter resultsAdapter = new SearchResultsAdapter(new ArrayList<>());
        searchResultsRecycler.setAdapter(resultsAdapter);
        searchResultsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // SEARCH BAR
        SearchView searchView = view.findViewById(R.id.searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProducts(query, resultsAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText, resultsAdapter);
                return true;
            }
        });

        return view;
    }

    private void filterProducts(String query, SearchResultsAdapter adapter) {
        List<String> filtered = new ArrayList<>();

        for (String product : allProducts) {
            if (product.toLowerCase().contains(query.toLowerCase())) {
                filtered.add(product);
            }
        }

        adapter.updateResults(filtered);
    }
}