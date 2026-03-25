package com.example.getgroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// This file is the fragment for one of the Grocery categories
public class DrinksFragment extends Fragment
{




    public DrinksFragment() {
        // Required empty public constructor
    }


    public static DrinksFragment newInstance(String param1, String param2)
    {
        DrinksFragment fragment = new DrinksFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drinks, container, false);
    }
}