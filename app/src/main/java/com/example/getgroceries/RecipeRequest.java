package com.example.getgroceries;

import java.util.List;

public class RecipeRequest {


    private List<String> ingredients;
    private List<String> dietary_restrictions;
    private String cuisine;
    private String meal_type;
    private int servings;
    private String lang;

    // Constructor
    public RecipeRequest(List<String> ingredients, List<String> dietary_restrictions, String cuisine, String meal_type, int servings, String lang)
    {
        this.ingredients = ingredients;
        this.dietary_restrictions = dietary_restrictions;
        this.cuisine = cuisine;
        this.meal_type = meal_type;
        this.servings = servings;
        this.lang = lang;
    }

    // Getters
    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getDietary_restrictions() {
        return dietary_restrictions;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public int getServings() {
        return servings;
    }

    public String getLang() {
        return lang;
    }
}