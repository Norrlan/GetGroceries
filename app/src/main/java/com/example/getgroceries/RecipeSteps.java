package com.example.getgroceries;
// Recipe steps data model for the recipe steps that will be fetch recipe stpes as a response from the API
public class RecipeSteps
{
    public int number;
    public String title;
    public String description;

    public RecipeSteps(int number, String title, String description)
    {
        this.number = number;
        this.title = title;
        this.description = description;
    }

}
