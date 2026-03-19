package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;
import java.util.List;

// Wraps the complexSearch response
public class RecipeSearchResponse {

    @SerializedName("results")
    private List<RecipeSearchResult> results;

    @SerializedName("totalResults")
    private int totalResults;

    public List<RecipeSearchResult> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }
}