package com.example.getgroceries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecipesFragment extends Fragment {

    private RecyclerView recyclerView;
    private SavedRecipeAdapter adapter;
    private List<SavedRecipe> recipeList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recipes_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SavedRecipeAdapter(recipeList, new SavedRecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(SavedRecipe recipe) {
                Fragment details = RecipeDetailsFragment.newInstance(recipe.title);
                ((MainActivity) requireActivity()).openFragment(details);
            }

            @Override
            public void onDeleteClick(SavedRecipe recipe) {
                deleteRecipe(recipe);
            }
        });

        recyclerView.setAdapter(adapter);

        loadRecipesFromFirebase();

        FloatingActionButton fab = view.findViewById(R.id.fabAddRecipe);
        fab.setOnClickListener(v -> {
            Fragment createFragment = new CreateRecipeFragment();
            ((MainActivity) requireActivity()).openFragment(createFragment);
        });
    }

    private void loadRecipesFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("recipes")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    recipeList.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        SavedRecipe recipe = doc.toObject(SavedRecipe.class);
                        recipe.id = doc.getId(); // attach Firestore ID
                        recipeList.add(recipe);
                    }

                    adapter.notifyDataSetChanged();
                });
    }

    private void deleteRecipe(SavedRecipe recipe) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("recipes")
                .document(recipe.id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    recipeList.remove(recipe);
                    adapter.notifyDataSetChanged();
                });
    }
}