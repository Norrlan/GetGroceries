package com.example.getgroceries;


import java.util.ArrayList;

public class CategoryList
{
    private static CategoryList instance;
    private static CategoryList getInstance = null;
    public CategoryList()
    {
        asdaAisles.add("Drinks");// Fruit
        asdaAisles.add("Snacks");// Bakery
        asdaAisles.add("Frozen Food");//
        asdaAisles.add("Bakery");
        asdaAisles.add("Canned Food");
    }
    public static CategoryList getInstance()
    {
        if ( instance == null)
        {
            instance = new CategoryList();
        }
        return  instance;
    }
    ArrayList<String>asdaAisles = new ArrayList<>();

}

