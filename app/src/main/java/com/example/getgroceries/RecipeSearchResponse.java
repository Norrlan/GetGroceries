package com.example.getgroceries;

import com.google.gson.annotations.SerializedName;
import java.util.List;

// Model class representing the response from Spoonacular's complexSearch endpoint.
// This endpoint returns a list of lightweight recipe results (id, title, image)
// along with the total number of results available.

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