package de.androidbytes.recipr.presentation.overview.core.model;

import lombok.Getter;

@Getter
public class RecipeViewModel {

    private long recipeId;
    private String recipeTitle;
    private boolean favorite;
    private String imageUrl;

    public RecipeViewModel(long recipeId, String recipeTitle, boolean favorite, String imageUrl) {
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.favorite = favorite;
        this.imageUrl = imageUrl;
    }
}
