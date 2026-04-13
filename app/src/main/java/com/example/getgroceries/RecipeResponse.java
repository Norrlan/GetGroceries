package com.example.getgroceries;
// Simple wrapper class used when an API returns a single RecipeModel

public class RecipeResponse
{

    RecipeModel result;

    public RecipeModel getResult() {
        return result;
    }
}