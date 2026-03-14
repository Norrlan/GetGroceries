package com.example.getgroceries;

public class IngredientModel {

   public String name;
   public String amount;

   // Required empty constructor for Gson/Retrofit
   public IngredientModel() {}

   public String getName() {
      return name;
   }

   public String getAmount() {
      return amount;
   }
}