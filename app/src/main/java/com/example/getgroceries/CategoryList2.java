package com.example.getgroceries;

import java.util.ArrayList;

public class CategoryList2
{
    private static CategoryList2 instance;
    private static CategoryList2 getInstance = null;
    public CategoryList2()
    {
        ebayAisles.add("Fruit");
        ebayAisles.add("Bakery");
        ebayAisles.add("Frozen Food");
        ebayAisles.add("Laundry");
    }
    public static CategoryList2 getInstance()
    {
        if ( instance == null)
        {
            instance = new CategoryList2();
        }
        return  instance;
    }
    ArrayList<String> ebayAisles = new ArrayList<>();
    public ArrayList<String> getEbayAisles()
    {
        return ebayAisles;
    }


}
