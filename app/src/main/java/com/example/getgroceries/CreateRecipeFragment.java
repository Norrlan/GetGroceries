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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateRecipeFragment extends Fragment
{

    private static final String API_KEY = "5c410c00a31c4958a650170e1db88d03"; // api key from spoonacular
    private static final String BASE_URL = "https://api.spoonacular.com/"; // spoonacular api website

    //initialise all  datafields  involved on the createRecipe Fragemnt
    private EditText recipeInput;
    private EditText urlInput;
    private Button generateButton;
    private Button extractButton;
    private ProgressBar loadingBar;

    private RecipeApiService apiService;

    public CreateRecipeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //inflate the fragments layout
        return inflater.inflate(R.layout.fragment_create_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Bind views
        recipeInput   = view.findViewById(R.id.recipe_input);
        urlInput      = view.findViewById(R.id.url_input);
        generateButton = view.findViewById(R.id.generate_button);
        extractButton  = view.findViewById(R.id.extract_button);
        loadingBar    = view.findViewById(R.id.loading_bar);

        // Build Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService = retrofit.create(RecipeApiService.class); // call the spoonacular api service

        // Enable the Generate Recipe button
        generateButton.setOnClickListener(v ->
        {
            String recipeName = recipeInput.getText().toString().trim();
            if (TextUtils.isEmpty(recipeName))
            {
                Toast.makeText(getContext(), "Please enter a recipe name", Toast.LENGTH_SHORT).show();
                return;
            }
            searchAndOpenRecipe(recipeName);
        });

        // Extract  Recipe button
        extractButton.setOnClickListener(v ->
        {
            String url = urlInput.getText().toString().trim();
            if (TextUtils.isEmpty(url))
            {
                Toast.makeText(getContext(), "Please paste a URL", Toast.LENGTH_SHORT).show();
                return;
            }
            extractAndOpenRecipe(url);
        });
    }

    // STEP 1: search by name → get id → STEP 2: get full info
    private void searchAndOpenRecipe(String recipeName)
    {
        setLoading(true);

        apiService.searchRecipes(recipeName, 1, "5c410c00a31c4958a650170e1db88d03").enqueue(new Callback<RecipeSearchResponse>()
        {
                    @Override
                    public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {

                        if (!response.isSuccessful() || response.body() == null || response.body().getResults() == null || response.body().getResults().isEmpty())
                        {
                            setLoading(false);
                            Toast.makeText(getContext(), "No recipe found for: " + recipeName + " Code: " + response.code(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        // Get the first result's id
                        int recipeId = response.body().getResults().get(0).getId();

                        //  get the full details of the recipe. will be shown in the recipe detaisl fragment.
                        getRecipeDetails(recipeId);
                    }

                    @Override
                    public void onFailure(Call<RecipeSearchResponse> call,
                                          Throwable t) {
                        setLoading(false);
                        Toast.makeText(getContext(),
                                "Search failed: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Get full recipe info by id and open details screen
    private void getRecipeDetails(int recipeId)
    {

        apiService.getRecipeById(recipeId, true, true, "5c410c00a31c4958a650170e1db88d03").enqueue(new Callback<RecipeModel>() {
                    @Override
                    public void onResponse(Call<RecipeModel> call, Response<RecipeModel> response)
                    {
                        setLoading(false);

                        if (!response.isSuccessful() || response.body() == null)
                        {
                            Toast.makeText(getContext(), "Could not load recipe details", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        openDetailsFragment(response.body());
                    }

                    @Override
                    public void onFailure(Call<RecipeModel> call,
                                          Throwable t) {
                        setLoading(false);
                        Toast.makeText(getContext(),
                                "Failed: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Extract recipe from a website URL
    private void extractAndOpenRecipe(String url)
    {
        setLoading(true);

        apiService.extractRecipeFromUrl(url, true, "5c410c00a31c4958a650170e1db88d03").enqueue(new Callback<RecipeModel>()
                {
                    @Override
                    public void onResponse(Call<RecipeModel> call,
                                           Response<RecipeModel> response)
                    {
                        setLoading(false);

                        //If it fails to produce the recipe as a result provide the response code as a Toast.
                        // Mainly to check if I have ran out of requests for the API. I might get 920 or 420 if the request I am allowed is 200
                        if (!response.isSuccessful() || response.body() == null)
                        {
                            Toast.makeText(getContext(), "Could not extract recipe from URL" + " Code: " + response.code(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        openDetailsFragment(response.body());
                    }

                    //same as above but its not API related
                    @Override
                    public void onFailure(Call<RecipeModel> call, Throwable t)
                    {
                        setLoading(false);
                        Toast.makeText(getContext(), "Extraction failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Opens RecipeDetailsFragment passing the full RecipeModel
    private void openDetailsFragment(RecipeModel recipe)
    {
        Fragment detailsFragment = RecipeDetailsFragment.newInstance(recipe.getTitle());
        ((MainActivity) requireActivity()).openFragment(detailsFragment);
    }

    //function to display the loadinf bar briefly when creating a recipe or extracting one.
    private void setLoading(boolean isLoading)
    {
        loadingBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        generateButton.setEnabled(!isLoading);
        extractButton.setEnabled(!isLoading);
    }
}