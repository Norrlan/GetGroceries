package com.example.getgroceries;


import java.util.ArrayList;

public class CategoryList
{
    private static CategoryList instance;
    private static CategoryList getInstance = null;
    public CategoryList()
    {
        asdaAisles.add("Fruit");
        asdaAisles.add("Bakery");
        asdaAisles.add("Frozen Food");
        asdaAisles.add("Laundry");
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

