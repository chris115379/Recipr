package de.androidbytes.recipr.presentation.single.detail.model.mapper;

import de.androidbytes.recipr.domain.model.Ingredient;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.model.Step;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.single.core.model.IngredientViewModel;
import de.androidbytes.recipr.presentation.single.detail.model.RecipeDetails;
import de.androidbytes.recipr.presentation.single.core.model.StepViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeToRecipeDetailsMapper implements DataMapper<Recipe, RecipeDetails> {

    DataMapper<Ingredient, IngredientViewModel> ingredientMapper;

    DataMapper<Step, StepViewModel> stepMapper;

    public RecipeToRecipeDetailsMapper(DataMapper<Ingredient, IngredientViewModel> ingredientMapper, DataMapper<Step, StepViewModel> stepMapper) {
        this.ingredientMapper = ingredientMapper;
        this.stepMapper = stepMapper;
    }


    @Override
    public RecipeDetails transform(Recipe model) {
        return new RecipeDetails(
                model.getId(),
                model.getName(),
                model.isFavorite(),
                model.getImageUrl(),
                model.getCategory().getName(),
                ingredientMapper.transform(model.getIngredients()),
                stepMapper.transform(model.getSteps())
        );
    }

    @Override
    public List<RecipeDetails> transform(Collection<Recipe> modelCollection) {

        List<RecipeDetails> recipeDetails = new ArrayList<>(modelCollection.size());
        for (Recipe recipe : modelCollection) {
            recipeDetails.add(transform(recipe));
        }
        return recipeDetails;
    }
}
