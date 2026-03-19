package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;

// Represents a single result from complexSearch
public class RecipeSearchResult
{

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getImage() { return image; }
}