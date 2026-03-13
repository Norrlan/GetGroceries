// inner model for containing actual recipe models that will be called by tbe API
package com.example.getgroceries;
import java.util.List;

public class RecipeModel
{
    String title;

   List <IngredientModel> ingredients;

    List <String> instructions;
    NutritionModel nutritionInfo;

    //Getters


    public String getTitle()
    {
        return title;
    }

    public List<IngredientModel> getIngredients()
    {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public NutritionModel getNutritionInfo()
    {
        return nutritionInfo;
    }




}
