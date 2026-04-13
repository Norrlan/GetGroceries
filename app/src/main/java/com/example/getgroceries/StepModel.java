package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;

public class StepModel
{

    @SerializedName("number")
    private int number;

    @SerializedName("step")
    private String step;

    public int getNumber()
    {
        return number;
    }
    public String getStep()
    {
        return step;
    }
}