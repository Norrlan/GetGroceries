package com.example.getgroceries;
// Model class representing a recipe saved locally by the user.
// This class is used for storing recipe details inside Firestore

public class SavedRecipe
{
    public String id;
    public String title;
    public String ingredients;
    public String instructions;
    public String nutrition;

    public SavedRecipe() {}

    public SavedRecipe(String title, String ingredients,
                       String instructions, String nutrition)
    {
        this.id = null; // Firebase will auto-generate this
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.nutrition = nutrition;
    }
}