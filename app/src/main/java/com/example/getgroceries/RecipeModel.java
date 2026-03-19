// Model class representing a Recipe as defined in the spoonacular documentation
package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class RecipeModel
{

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("readyInMinutes")
    private int readyInMinutes;

    @SerializedName("servings")
    private int servings;

    @SerializedName("extendedIngredients")
    private List<IngredientModel> ingredients;

    @SerializedName("analyzedInstructions")
    private List<InstructionWrapper> analyzedInstructions;

    @SerializedName("nutrition")
    private NutritionWrapper nutrition;

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getImage() { return image; }
    public int getReadyInMinutes() { return readyInMinutes; }
    public int getServings() { return servings; }
    public List<IngredientModel> getIngredients() { return ingredients; }

    //method to extract the steps in the instruction from the Instruction wrapper.
    // Then flatten the stpes into a List
    // display the formatted stpes with each iteration if it exists
    public List<String> getInstructions()
    {
        List<String> steps = new ArrayList<>();
        if (analyzedInstructions != null)
        {
            for (InstructionWrapper wrapper : analyzedInstructions)
            {
                if (wrapper.getSteps() != null)
                {
                    for (StepModel step : wrapper.getSteps())
                    {
                        steps.add("Step " + step.getNumber() + ": " + step.getStep());
                    }
                }
            }
        }

        return steps;
    }

    // method to get the nutrition information from the Nutrition Model by converting the nutrition data if available
    public NutritionModel getNutrition_info()
    {
        if (nutrition != null)
        {
            return nutrition.toNutritionModel();
        }
        return null;
    }
}