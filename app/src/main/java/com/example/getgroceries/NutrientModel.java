// Model class representing a nutrient as defined by the Spoonacular API nutrition data structure.
package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;


public class NutrientModel
{

    @SerializedName("name")
    private String name;

    @SerializedName("amount")
    private double amount;

    @SerializedName("unit")
    private String unit;

    public String getName() { return name; }
    public double getAmount() { return amount; }
    public String getUnit() { return unit; }
}