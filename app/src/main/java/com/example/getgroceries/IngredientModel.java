package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;
import android.os.Bundle;

// Ingredient data model for the recipe ingredients that will be fetch ingredients as a response from the API
public class IngredientModel
{
   @SerializedName("raw_text")
   public String rawText;

   public String quantity;
   public String name;
// placeholder constructor:
public IngredientModel(String quantity, String name) {
   this.quantity = quantity;
   this.name = name;
}


}










//placeholder data
    /*
    *  public String quantity;
    public String name;

    public IngredientModel(String quantity, String name)
    {
        this.name= name;
        this.quantity = quantity;
    }
    * */