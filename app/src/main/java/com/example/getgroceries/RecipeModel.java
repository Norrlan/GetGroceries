// inner model for containing actual The actual recipe that will be called by tbe API and displayed in Ui
package com.example.getgroceries;
import java.util.List;
import java.lang.String;

public class RecipeModel
{
    String title;

   List <IngredientModel> ingredients;

    List <String> instructions;
    NutritionModel nutrition_info;

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

    public NutritionModel getNutrition_info()
    {
        return nutrition_info;
    }
}
