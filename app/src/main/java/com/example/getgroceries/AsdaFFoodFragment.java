package com.example.getgroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AsdaFFoodFragment extends Fragment
{

    public AsdaFFoodFragment()
    {
        // Required empty public constructor
    }

    public static AsdaFFoodFragment newInstance(String param1, String param2)
    {
        AsdaFFoodFragment fragment = new AsdaFFoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asda_f_food, container, false);
    }
}