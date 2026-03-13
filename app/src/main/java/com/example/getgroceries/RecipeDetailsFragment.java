package com.example.getgroceries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

// This screen shows a single recipe’s details
public class RecipeDetailsFragment extends Fragment
{

    private static final String ARG_RECIPE_NAME = "recipe_name";
    private String recipeName;

    public static RecipeDetailsFragment newInstance(String recipeName)

    {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RECIPE_NAME, recipeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            recipeName = getArguments().getString(ARG_RECIPE_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_recipe_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        // Placeholder button. It doesnt save for now
        TextView title = view.findViewById(R.id.recipe_title);
        RecyclerView ingredientsRecycler = view.findViewById(R.id.ingredients_recycler);
        RecyclerView stepsRecycler = view.findViewById(R.id.steps_recycler);
        //Save Button
        Button saveButton = view.findViewById(R.id.btnSaveRecipe);

        saveButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Recipe saved!", Toast.LENGTH_SHORT).show();

            // TODO: Save recipe to database or shared preferences
        });


        title.setText(recipeName);


        /*

        // Placeholder Ingredients
        List<IngredientModel> ingredients = Arrays.asList(
                new IngredientModel("2 lbs", "Chicken Breast"),
                new IngredientModel("1 cup", "Heavy Cream"),
                new IngredientModel("200g", "Pasta"),
                new IngredientModel("1 tbsp", "Butter"),
                new IngredientModel("1 tsp", "Garlic Powder")
        );
        // Placeholder Steps
        List<RecipeSteps> steps = Arrays.asList(
                new RecipeSteps(1, "Prepare Ingredients", "Chop chicken and gather all ingredients."),
                new RecipeSteps(2, "Cook Chicken", "Season chicken and cook until golden brown."),
                new RecipeSteps(3, "Make Alfredo Sauce", "Add cream and butter, simmer until thick."),
                new RecipeSteps(4, "Combine", "Mix pasta with sauce and chicken."),
                new RecipeSteps(5, "Serve", "Plate and garnish with parsley.")

        );
*/

        // Setup Recycler

        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        //ingredientsRecycler.setAdapter(new IngredientAdapter(ingredients));
        //stepsRecycler.setAdapter(new StepsAdapter(steps));
    }
}