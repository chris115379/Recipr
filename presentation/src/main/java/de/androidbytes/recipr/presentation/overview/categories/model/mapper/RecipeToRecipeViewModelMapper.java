package de.androidbytes.recipr.presentation.overview.categories.model.mapper;

import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@PerActivity
public class RecipeToRecipeViewModelMapper implements DataMapper<Recipe, RecipeViewModel> {

    @Inject
    public RecipeToRecipeViewModelMapper() {
    }

    public RecipeViewModel transform(Recipe recipe) {

        if (recipe == null) {
            throw new IllegalArgumentException("Cannot transform null value");
        }

        return new RecipeViewModel(
                recipe.getId(),
                recipe.getName(),
                recipe.isFavorite(),
                recipe.getImageUrl()
        );
    }

    public List<RecipeViewModel> transform(Collection<Recipe> recipes) {

        List<RecipeViewModel> recipeViewModels;

        if (recipes != null && !recipes.isEmpty()) {
            recipeViewModels = new ArrayList<>();
            for (Recipe recipe : recipes) {
                recipeViewModels.add(transform(recipe));
            }
        } else {
            recipeViewModels = Collections.emptyList();
        }

        return recipeViewModels;

    }

}
