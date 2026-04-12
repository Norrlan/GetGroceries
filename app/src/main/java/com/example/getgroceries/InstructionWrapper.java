// wrapper class for the  Steps Model.
package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class InstructionWrapper
{

    @SerializedName("steps")
    private List<StepModel> steps;

    public List<StepModel> getSteps()
    {
        return steps;
    }
}