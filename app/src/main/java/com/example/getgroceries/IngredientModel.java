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

   @SerializedName("original") // orginal ingredient data from the api
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
      if (unit != null && !unit.isEmpty()) // return the amount the user entered and the unit  if the
      {
         return amount + " " + unit;
      }
      return String.valueOf(amount);
   }
}