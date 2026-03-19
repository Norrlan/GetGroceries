// wrapper class for the Nutrient Model.
// Wraps raw nutrient data into NutritionModel.
package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class NutritionWrapper
{

    @SerializedName("nutrients")
    private List<NutrientModel> nutrients;

    public NutritionModel toNutritionModel()
    {
        NutritionModel model = new NutritionModel();
        if (nutrients != null)
        {
            for (NutrientModel n : nutrients)
            {
                switch (n.getName())
                {
                    case "Calories":
                        model.setCalories(n.getAmount() + " " + n.getUnit());
                        break;
                    case "Protein":
                        model.setProtein(n.getAmount() + " " + n.getUnit());
                        break;
                    case "Carbohydrates":
                        model.setCarbs(n.getAmount() + " " + n.getUnit());
                        break;
                    case "Fat":
                        model.setFat(n.getAmount() + " " + n.getUnit());
                        break;
                }
            }
        }
        return model;
    }
}