package com.example.getgroceries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeDetailsFragment extends Fragment {

    private static final String ARG_RECIPE_NAME = "recipe_name";
    private static final String API_KEY = "5c410c00a31c4958a650170e1db88d03";
    private static final String BASE_URL = "https://api.spoonacular.com/";

    private String recipeName;

    private TextView titleView, ingredientsView, instructionsView, nutritionView;
    private ProgressBar loadingBar;
    private RecipeApiService recipeApiService;

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

        // Bind views
        titleView        = view.findViewById(R.id.recipe_title);
        ingredientsView  = view.findViewById(R.id.ingredients_text);
        instructionsView = view.findViewById(R.id.instructions_text);
        nutritionView    = view.findViewById(R.id.nutrition_text);
        loadingBar       = view.findViewById(R.id.loading_bar);
        Button saveButton = view.findViewById(R.id.btnSaveRecipe);

        titleView.setText(recipeName);

        // Save button — stores displayed recipe data to Firestore
        saveButton.setOnClickListener(v -> {
            SavedRecipe savedRecipe = new SavedRecipe(
                    titleView.getText().toString(),
                    ingredientsView.getText().toString(),
                    instructionsView.getText().toString(),
                    nutritionView.getText().toString()
            );

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("recipes")
                    .add(savedRecipe)
                    .addOnSuccessListener(ref ->
                            Toast.makeText(getContext(),
                                    "Recipe saved!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(),
                                "Failed: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                        android.util.Log.e("FIREBASE", "Save failed", e);
                    });
        });

        // Build Retrofit — now pointing to Spoonacular
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recipeApiService = retrofit.create(RecipeApiService.class);

        callApiForRecipe();
    }

    // Spoonacular API function: search by name to get recipe id
    // Function to call the recipe api
    private void callApiForRecipe()
    {
        loadingBar.setVisibility(View.VISIBLE);

        recipeApiService.searchRecipes(recipeName, 1, "5c410c00a31c4958a650170e1db88d03")
                .enqueue(new Callback<RecipeSearchResponse>()
                {
                    // onResponse function to manage the response for the API function that lets users search by the name to get the recipe id.

                    @Override
                    public void onResponse(Call<RecipeSearchResponse> call,
                                           Response<RecipeSearchResponse> response)
                    {
                        //if it cannot find the recipe after searching the toast message should tell the user its cannot find the recipe
                        if (!response.isSuccessful()
                                || response.body() == null
                                || response.body().getResults() == null
                                || response.body().getResults().isEmpty())
                        {

                            loadingBar.setVisibility(View.GONE); Toast.makeText(getContext(), "Recipe not found: " + recipeName, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get id from first result
                        int id = response.body().getResults().get(0).getId();

                        // get full details using id
                        getRecipeDetails(id);
                    }

                    // function if it fails to call the API.
                    @Override
                    public void onFailure(Call<RecipeSearchResponse> call, Throwable t)
                    {
                        loadingBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(),
                                "Search failed: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Spoonacular API function: get full recipe details and display them
    private void getRecipeDetails(int recipeId)
    {

        recipeApiService.getRecipeById(recipeId, true, true,"5c410c00a31c4958a650170e1db88d03")
                .enqueue(new Callback<RecipeModel>()
                {

                    @Override
                    public void onResponse(Call<RecipeModel> call, Response<RecipeModel> response)
                    {

                        loadingBar.setVisibility(View.GONE);

                        if (!response.isSuccessful() || response.body() == null)
                        {
                            Toast.makeText(getContext(),
                                    "Could not load recipe details",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        displayRecipe(response.body());
                    }

                    @Override
                    public void onFailure(Call<RecipeModel> call, Throwable t)
                    {
                        loadingBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(),
                                "Failed: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Displays all recipe data in the UI
    private void displayRecipe(RecipeModel recipe) {

        // Title
        titleView.setText(recipe.getTitle());

        // INGREDIENTS
        if (recipe.getIngredients() != null
                && !recipe.getIngredients().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (IngredientModel ing : recipe.getIngredients()) {
                sb.append("• ").append(ing.getAmount()).append("\n");
            }
            ingredientsView.setText(sb.toString());
        } else {
            ingredientsView.setText("No ingredients available.");
        }

        // INSTRUCTIONS
        if (recipe.getInstructions() != null
                && !recipe.getInstructions().isEmpty()) {
            instructionsView.setText(formatList(recipe.getInstructions()));
        } else {
            instructionsView.setText("No instructions available.");
        }

        // NUTRITION
        if (recipe.getNutrition_info() != null) {
            nutritionView.setText(formatNutrition(recipe.getNutrition_info()));
        } else {
            nutritionView.setText("No nutrition info available.");
        }
    }

    // Formats a List<String> into a bulleted string
    private String formatList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String item : list) {
            builder.append("• ").append(item).append("\n");
        }
        return builder.toString();
    }

    // Formats NutritionModel into a readable string
    private String formatNutrition(NutritionModel nutrition) {
        StringBuilder builder = new StringBuilder();

        if (nutrition.getCalories() != null)
            builder.append("• Calories: ").append(nutrition.getCalories()).append("\n");
        if (nutrition.getFat() != null)
            builder.append("• Fat: ").append(nutrition.getFat()).append("\n");
        if (nutrition.getProtein() != null)
            builder.append("• Protein: ").append(nutrition.getProtein()).append("\n");
        if (nutrition.getCarbs() != null)
            builder.append("• Carbs: ").append(nutrition.getCarbs()).append("\n");

        if (builder.length() == 0)
            return "No nutrition info available.";

        return builder.toString();
    }
}