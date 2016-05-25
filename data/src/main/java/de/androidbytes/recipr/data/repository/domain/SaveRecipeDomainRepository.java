package de.androidbytes.recipr.data.repository.domain;

import de.androidbytes.recipr.data.entity.*;
import de.androidbytes.recipr.data.entity.mapper.RecipeMapper;
import de.androidbytes.recipr.data.repository.data.*;
import de.androidbytes.recipr.domain.model.Ingredient;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.model.Step;
import de.androidbytes.recipr.domain.repository.SaveRecipeRepository;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class SaveRecipeDomainRepository implements SaveRecipeRepository {

    @Inject
    UserDataRepository userRepository;

    @Inject
    CategoryDataRepository categoryRepository;

    @Inject
    RecipeDataRepository recipeRepository;

    @Inject
    IngredientDataRepository ingredientRepository;

    @Inject
    StepDataRepository stepRepository;

    @Inject
    RecipeMapper recipeMapper;

    @Inject
    public SaveRecipeDomainRepository() {
    }

    @Override
    public Observable<Recipe> saveRecipe(long userId, Recipe recipe) {

        UserEntity userEntity = userRepository.findById(userId);

        CategoryEntity savedCategoryEntity = categoryRepository.createOrUpdate(new CategoryEntity(recipe.getCategory()));
        long categoryId = savedCategoryEntity.getId();

        final RecipeEntity savedRecipeEntity = recipeRepository.createRecipe(new RecipeEntity(recipe, userId, categoryId));
        long recipeId = savedRecipeEntity.getId();

        List<Ingredient> ingredients = recipe.getIngredients();
        List<IngredientEntity> savedIngredientEntities = new ArrayList<>(ingredients.size());
        for (Ingredient ingredient : ingredients) {
            IngredientEntity savedIngredientEntity = ingredientRepository.createIngredient(new IngredientEntity(ingredient, recipeId));
            savedIngredientEntities.add(savedIngredientEntity);
        }

        List<Step> steps = recipe.getSteps();
        List<StepEntity> savedStepEntities = new ArrayList<>(steps.size());
        for (Step step : steps) {
            StepEntity savedStepEntity = stepRepository.createStep(new StepEntity(step, recipeId));
            savedStepEntities.add(savedStepEntity);
        }

        Recipe savedRecipe = recipeMapper.map(savedRecipeEntity, savedIngredientEntities, savedStepEntities, userEntity, savedCategoryEntity);
        return Observable.just(savedRecipe);
    }
}
