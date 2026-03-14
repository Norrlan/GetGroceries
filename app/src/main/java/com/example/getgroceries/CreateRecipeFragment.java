package com.example.getgroceries;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateRecipeFragment extends Fragment
{

    private EditText recipeInput;
    private Button generateButton;
    private ProgressBar loadingBar;

    public CreateRecipeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_create_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipeInput = view.findViewById(R.id.recipe_input);
        generateButton = view.findViewById(R.id.generate_button);
        loadingBar = view.findViewById(R.id.loading_bar);

        generateButton.setOnClickListener(v -> {

            String recipeName = recipeInput.getText().toString().trim();

            if (TextUtils.isEmpty(recipeName)) {
                Toast.makeText(getContext(), "Please enter a recipe name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show loading briefly (optional)
            loadingBar.setVisibility(View.VISIBLE);
            generateButton.setEnabled(false);

            // Navigate immediately to RecipeDetailsFragment
            Fragment detailsFragment = RecipeDetailsFragment.newInstance(recipeName);
            ((MainActivity) requireActivity()).openFragment(detailsFragment);

            // Reset UI
            loadingBar.setVisibility(View.GONE);
            generateButton.setEnabled(true);
        });
    }
}