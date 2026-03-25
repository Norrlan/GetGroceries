package com.example.getgroceries;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // CATEGORY RECYCLER

        RecyclerView categoryRecycler = view.findViewById(R.id.rvCategories);

        List<Integer> categoryImages = Arrays.asList(
                R.drawable.drinks_card,
                R.drawable.snacks,
                R.drawable.frozenfood,
                R.drawable.condiment
        );

        List<String> categoryLabels = Arrays.asList(
                "Drinks",
                "Snacks",
                "Frozen",
                "Condiments"
        );

        Searchadapter adapter = new Searchadapter(categoryImages, categoryLabels, position -> {
            openCategory(position);
        });

        categoryRecycler.setAdapter(adapter);
        categoryRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // SEARCH RESULTS RECYCLER

        RecyclerView searchResultsRecycler = view.findViewById(R.id.search_results_recycler);
        SearchResultsAdapter resultsAdapter = new SearchResultsAdapter(new ArrayList<>());
        searchResultsRecycler.setAdapter(resultsAdapter);
        searchResultsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // EMPTY STATE
        View emptyState = view.findViewById(R.id.EmptyState);

        // SEARCH BAR

        TextInputEditText searchBar = view.findViewById(R.id.Search_bar);

        searchBar.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count)
            {
                String query = text.toString().trim();

                if (query.isEmpty())
                {
                    emptyState.setVisibility(View.VISIBLE);
                    searchResultsRecycler.setVisibility(View.GONE);
                }

                else
                {
                    emptyState.setVisibility(View.GONE);
                    searchResultsRecycler.setVisibility(View.VISIBLE);

                    // API search will go here later
                    resultsAdapter.updateResults(new ArrayList<>());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    // CATEGORY NAVIGATION

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
                fragment = new FrozenFragment();
                break;
            case 3:
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
}