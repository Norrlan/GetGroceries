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
        // inflate the layout
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Recycler view that displays results

        RecyclerView searchResultsRecycler = view.findViewById(R.id.search_results_recycler);
        SearchResultsAdapter resultsAdapter = new SearchResultsAdapter(new ArrayList<>());
        searchResultsRecycler.setAdapter(resultsAdapter);
        searchResultsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        //Search baer
        TextInputEditText searchBar = view.findViewById(R.id.Search_bar);

        searchBar.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count)
            {
                String query = text.toString().trim();
                searchResultsRecycler.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE );

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

}