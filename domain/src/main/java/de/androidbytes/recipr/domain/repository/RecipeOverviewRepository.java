package de.androidbytes.recipr.domain.repository;

import de.androidbytes.recipr.domain.model.CategoryRecipeAggregate;
import de.androidbytes.recipr.domain.model.Recipe;
import rx.Observable;

import java.util.List;

public interface RecipeOverviewRepository {

    Observable<List<CategoryRecipeAggregate>> findAllCategories(long userId, int recipesPerCategoryCount);

    Observable<List<Recipe>> findAllFavorites(long userId);
    
    Observable<List<Recipe>> getRecipesOfCategory(long userId, String categoryName);

    List<Recipe> findRecipesOfUserByName(long userId, String searchRecipes);
}
