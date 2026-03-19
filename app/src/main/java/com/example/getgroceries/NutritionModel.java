// Model class representing the Nutrition aspect of recipes as defined in the spoonacular documentation
package com.example.getgroceries;

public class NutritionModel
{

    private String calories;
    private String protein;
    private String carbs;
    private String fat;

    public NutritionModel() {}

    // Getters
    public String getCalories() { return calories; }
    public String getProtein() { return protein; }
    public String getCarbs() { return carbs; }
    public String getFat() { return fat; }

    // Setters going to be used in the NutritionWrapper class

    public void setCalories(String calories) { this.calories = calories; }
    public void setProtein(String protein) { this.protein = protein; }
    public void setCarbs(String carbs) { this.carbs = carbs; }
    public void setFat(String fat) { this.fat = fat; }
}