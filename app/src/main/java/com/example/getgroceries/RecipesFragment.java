package com.example.getgroceries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

// Show the list of generated recipes in this fragment
//With the API it calls API, shows list.

public class RecipesFragment extends Fragment
{

    public RecipesFragment() {
        // Required empty public constructor
        // X-Rapid Api Key: febfb97138msh1eaa457ca4ea420p1c9263jsn440b109d0eed
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fabAddRecipe);

        fab.setOnClickListener(v -> {
            Fragment createFragment = new CreateRecipeFragment();
            ((MainActivity) requireActivity()).openFragment(createFragment);
        });
    }
}