package com.example.getgroceries;
// Interface for the Tasty API to define API endpoints and their request types.


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RecipeApiService
{

    @Headers({
            "Content-Type: application/json",
            "X-RapidAPI-Key: febfb97138msh1eaa457ca4ea420p1c9263jsn440b109d0eed",
            "X-RapidAPI-Host: ai-food-recipe-generator-api-custom-diet-quick-meals.p.rapidapi.com"

    })
    @POST("generate")
    Call<RecipeResponse> generateRecipe(
            @Query("noqueue") int noQueue,
            @Body RecipeRequest requestBody
    );


}
