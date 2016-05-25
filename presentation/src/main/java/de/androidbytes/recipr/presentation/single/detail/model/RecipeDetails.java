package de.androidbytes.recipr.presentation.single.detail.model;

import de.androidbytes.recipr.presentation.single.core.model.IngredientViewModel;
import de.androidbytes.recipr.presentation.single.core.model.StepViewModel;
import lombok.Getter;

import java.util.List;

@Getter
public class RecipeDetails {

    private long recipeId;
    private String name;
    private boolean favorite;
    private String imageUrl;
    private String categoryTitle;

    private List<IngredientViewModel> ingredients;
    private List<StepViewModel> steps;

    public RecipeDetails(long recipeId, String name, boolean favorite, String imageUrl, String categoryTitle, List<IngredientViewModel> ingredients, List<StepViewModel> steps) {
        this.recipeId = recipeId;
        this.name = name;
        this.favorite = favorite;
        this.imageUrl = imageUrl;
        this.categoryTitle = categoryTitle;
        this.ingredients = ingredients;
        this.steps = steps;
    }
}
