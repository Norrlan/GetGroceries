package com.example.getgroceries;
// This file contains all the categories within the Grocery API.

import java.util.ArrayList;

public class GroceryCategoryList
{
    private static GroceryCategoryList instance;
    private static GroceryCategoryList getInstance = null;
    public GroceryCategoryList()
    {
        asdaAisles.add("Drinks");// Fruit
        asdaAisles.add("Snacks");// Bakery
        asdaAisles.add("Frozen Food");//
        asdaAisles.add("Bakery");
        asdaAisles.add("Canned Food");
    }
    public static GroceryCategoryList getInstance()
    {
        if ( instance == null)
        {
            instance = new GroceryCategoryList();
        }
        return  instance;
    }
    ArrayList<String>asdaAisles = new ArrayList<>();

}

