package com.example.getgroceries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

// Show the list of generated recipes in this fragment
//With the API it calls API, shows list.

public class RecipesFragment extends Fragment
{

    private RecipeApiService recipeApiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrofit building
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ai-food-recipe-generator-api-custom-diet-quick-meals.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recipeApiService = retrofit.create(RecipeApiService.class);

        // Build request
        List<String> ingredients = Arrays.asList("chicken", "rice", "pepper");
        List<String> restrictions = Arrays.asList("gluten_free");

        RecipeRequest request = new RecipeRequest(
                ingredients,
                restrictions,
                "Italian",
                "dinner",
                3,
                "en"
        );

        // Call API
        Call<RecipeResponse> call = recipeApiService.generateRecipe(1, request);

        call.enqueue(new Callback<RecipeResponse>()
        {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response)
            {

                if (!response.isSuccessful() || response.body() == null) {
                    // TODO: show error message
                    return;
                }

                RecipeModel recipeModel = response.body().getResult(); //'getResult()' has private access in 'com.example.getgroceries.RecipeResponse'

                // TODO: display recipe in UI
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t)
            {
                // TODO: show error message
            }
        });

        // FAB navigation
        FloatingActionButton fab = view.findViewById(R.id.fabAddRecipe);
        fab.setOnClickListener(v ->
        {
            Fragment createFragment = new CreateRecipeFragment();
            ((MainActivity) requireActivity()).openFragment(createFragment);
        });
    }
}

