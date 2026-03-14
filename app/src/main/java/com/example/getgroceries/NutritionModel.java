package com.example.getgroceries;

// Model class for nutrition
public class NutritionModel {

    private String calories;
    private String protein;
    private String carbs;
    private String fat;   // API uses "fat", not "fats"

    // Required empty constructor for Firebase / Retrofit
    public NutritionModel() {}

    // Getters
    public String getCalories() {
        return calories;
    }

    public String getProtein() {
        return protein;
    }

    public String getCarbs() {
        return carbs;
    }

    public String getFat() {
        return fat;
    }
}