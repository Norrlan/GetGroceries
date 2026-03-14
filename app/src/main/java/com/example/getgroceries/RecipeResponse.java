package com.example.getgroceries;


// Wrapper model: Matches the structure of the Recipe AI API  JSON response.
public class RecipeResponse
{

RecipeModel result;

RecipeModel ingredients;




// getters for the Response :
public RecipeModel getResult()
    {
        return result;
    }

}
