package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;

//Model for the ingredients that will be displayed in the RecipeDetailsFragment

public class IngredientModel
{

   @SerializedName("name")
   public String name;

   @SerializedName("amount")
   public double amount;

   @SerializedName("unit")
   public String unit;

   @SerializedName("original")
   public String original; // e.g. "2 cups of flour"

   public IngredientModel() {}

   public String getName()
   {
      return name;
   }

   // Returns "2.0 cups" or just the original text from the API
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