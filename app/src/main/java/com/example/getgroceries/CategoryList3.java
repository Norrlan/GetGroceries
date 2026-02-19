package com.example.getgroceries;

import java.util.ArrayList;

public class CategoryList3
{
    private static CategoryList3 instance;
    private static CategoryList3 getInstance = null;
    public CategoryList3()
    {
        IceAisles.add("Fruit");
        IceAisles.add("Bakery");
        IceAisles.add("Frozen Food");
        IceAisles.add("Laundry");
    }
    public static CategoryList3 getInstance()
    {
        if ( instance == null)
        {
            instance = new CategoryList3();
        }
        return  instance;
    }
    ArrayList<String> IceAisles = new ArrayList<>();
    public ArrayList<String> getIceAisles()
    {
        return IceAisles;
    }

}

