package com.example.getgroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// file is the category of frozen food
public class FrozenFragment extends Fragment {



    public FrozenFragment() {
        // Required empty public constructor
    }

    public static FrozenFragment newInstance(String param1, String param2) {
        FrozenFragment fragment = new FrozenFragment();
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
        return inflater.inflate(R.layout.fragment_frozen, container, false);
    }
}