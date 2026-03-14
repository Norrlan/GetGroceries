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

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeDetailsFragment extends Fragment
{

    private static final String ARG_RECIPE_NAME = "recipe_name";
    private String recipeName;

    private TextView titleView, ingredientsView, instructionsView, nutritionView;
    private ProgressBar loadingBar;
    private RecipeApiService recipeApiService;

    public static RecipeDetailsFragment newInstance(String recipeName) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RECIPE_NAME, recipeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeName = getArguments().getString(ARG_RECIPE_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind views
        titleView       = view.findViewById(R.id.recipe_title);
        ingredientsView = view.findViewById(R.id.ingredients_text);
        instructionsView= view.findViewById(R.id.instructions_text);
        nutritionView   = view.findViewById(R.id.nutrition_text);
        loadingBar      = view.findViewById(R.id.loading_bar);
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
                    .addOnFailureListener(e ->
                            Toast.makeText(getContext(),
                                    "Failed to save recipe", Toast.LENGTH_SHORT).show());
        });

        // Build Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ai-food-recipe-generator-api-custom-diet-quick-meals.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recipeApiService = retrofit.create(RecipeApiService.class);

        callApiForRecipe();
    }

    private void callApiForRecipe() {

        loadingBar.setVisibility(View.VISIBLE);

        List<String> ingredients   = Arrays.asList(recipeName);
        List<String> restrictions  = Arrays.asList();

        RecipeRequest request = new RecipeRequest(
                ingredients,
                restrictions,
                "",     // cuisine
                "",     // meal type
                2,      // servings
                "en"    // language
        );

        Call<RecipeResponse> call = recipeApiService.generateRecipe(1, request);

        call.enqueue(new Callback<RecipeResponse>() {

            @Override
            public void onResponse(Call<RecipeResponse> call,
                                   Response<RecipeResponse> response) {

                loadingBar.setVisibility(View.GONE);

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(getContext(),
                            "API Error: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                RecipeModel recipe = response.body().getResult();

                if (recipe == null) {
                    Toast.makeText(getContext(),
                            "No recipe data returned",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // INGREDIENTS
                if (recipe.getIngredients() != null
                        && !recipe.getIngredients().isEmpty()) {

                    StringBuilder sb = new StringBuilder();
                    for (IngredientModel ing : recipe.getIngredients()) {
                        sb.append("• ").append(ing.getName());
                        if (ing.getAmount() != null
                                && !ing.getAmount().isEmpty()) {
                            sb.append(" — ").append(ing.getAmount());
                        }
                        sb.append("\n");
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

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                loadingBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Request failed: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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