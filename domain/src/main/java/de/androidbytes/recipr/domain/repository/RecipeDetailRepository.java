package de.androidbytes.recipr.domain.repository;

import de.androidbytes.recipr.domain.model.Recipe;
import rx.Observable;

public interface RecipeDetailRepository {
    Observable<Recipe> getRecipeDetails(long userId, long recipeId);
    Observable<Recipe> changeFavorStateOfRecipe(long userId, long recipeId, boolean favorite);
}
