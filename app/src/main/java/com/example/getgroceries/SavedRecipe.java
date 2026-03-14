package com.example.getgroceries;

public class SavedRecipe {
    public String id;
    public String title;
    public String ingredients;
    public String instructions;
    public String nutrition;

    public SavedRecipe() {}

    public SavedRecipe(String title, String ingredients,
                       String instructions, String nutrition) {
        this.id = null; // Firebase will auto-generate this
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.nutrition = nutrition;
    }
}