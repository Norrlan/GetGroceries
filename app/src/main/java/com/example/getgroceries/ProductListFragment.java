package com.example.getgroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Fragment responsible for displaying products inside a selected subcategory.


public class ProductListFragment extends Fragment
{
private static final  String ARG_INDEX = "subcategory_index";
private static final String ARG_TITLE = "subcategory_title";

private int subcategoryIndex;
private String subcategoryTitle;

 public ProductListFragment()
    {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance(int index, String title)
    {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            subcategoryIndex = getArguments().getInt(ARG_INDEX);
            subcategoryTitle = getArguments().getString(ARG_TITLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        TextView titleView = view.findViewById(R.id.product_title);
        titleView.setText(subcategoryTitle);

        RecyclerView recyclerView = view.findViewById(R.id.product_recycler);

       // recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

}