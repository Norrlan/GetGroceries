package com.example.getgroceries;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// Retrofit interface defining all API endpoints used for recipe search,
// recipe details, and recipe extraction from external URLs.

public interface RecipeApiService
{
    // search by name, returns id , title and image
    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> searchRecipes(
            @Query("query") String query,
            @Query("number") int number,
            @Query("apiKey") String apiKey
    );

    // get recipe details by id
    @GET("recipes/{id}/information")
    Call<RecipeModel> getRecipeById(
            @Path("id") int id,
            @Query("includeNutrition") boolean includeNutrition,
            @Query("addRecipeInstructions") boolean addRecipeInstructions,
            @Query("apiKey") String apiKey
    );

    // Extract recipe from a website URL
    @GET("recipes/extract")
    Call<RecipeModel> extractRecipeFromUrl(
            @Query("url") String url,
            @Query("includeNutrition") boolean includeNutrition,
            @Query("apiKey") String apiKey
    );
}