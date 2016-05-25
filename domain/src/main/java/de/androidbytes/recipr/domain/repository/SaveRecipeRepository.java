package de.androidbytes.recipr.domain.repository;

import de.androidbytes.recipr.domain.model.Recipe;
import rx.Observable;

public interface SaveRecipeRepository {
    Observable<Recipe> saveRecipe(long userId, Recipe recipe);
}
