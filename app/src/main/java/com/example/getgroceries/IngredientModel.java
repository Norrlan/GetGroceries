package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;

/**
 * Model class representing a single ingredient in a recipe.
 * Maps to the 'extendedIngredients' array returned by the Spoonacular API
 * in the Get Recipe Information endpoint.
 * Each field uses @SerializedName to match the exact JSON key from the API response.
 */
public class IngredientModel
{
   @SerializedName("name")
   public String name;

   @SerializedName("amount")
   public double amount;

   @SerializedName("unit")
   public String unit;

   @SerializedName("original")
   public String original;

   public IngredientModel() {}

   public String getName()
   {
      return name;
   }

   // Method to get the original amount in each ingredient used  from the API.
   public String getAmount()
   {
      if (original != null && !original.isEmpty())
      {
         return original;
      }
      if (unit != null && !unit.isEmpty())
      {
         return amount + " " + unit;
      }
      return String.valueOf(amount);
   }
}