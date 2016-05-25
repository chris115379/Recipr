package de.androidbytes.recipr.data.repository.domain;

import de.androidbytes.recipr.data.entity.*;
import de.androidbytes.recipr.data.entity.mapper.RecipeMapper;
import de.androidbytes.recipr.data.repository.data.*;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.RecipeDetailRepository;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class RecipeDomainDetailRepository implements RecipeDetailRepository {

    @Inject
    RecipeDataRepository recipeRepository;

    @Inject
    IngredientDataRepository ingredientRepository;

    @Inject
    StepDataRepository stepRepository;

    @Inject
    UserDataRepository userRepository;

    @Inject
    CategoryDataRepository categoryRepository;

    @Inject
    RecipeMapper recipeMapper;

    @Inject
    public RecipeDomainDetailRepository() {
    }

    @Override
    public Observable<Recipe> getRecipeDetails(long userId, long recipeId) {
        Recipe recipe = loadDetails(userId, recipeId);
        return Observable.just(recipe);
    }

    @Override
    public Observable<Recipe> changeFavorStateOfRecipe(long userId, long recipeId, boolean favorite) {
        recipeRepository.changeFavorStateOfRecipe(userId, recipeId, favorite);
        return Observable.just(loadDetails(userId, recipeId));
    }

    private Recipe loadDetails(long userId, long recipeId) {
        UserEntity userEntity = userRepository.findById(userId);
        RecipeEntity recipeEntity = recipeRepository.findRecipeOfUserById(userId, recipeId);
        CategoryEntity categoryEntity = categoryRepository.findCategoryById(userId, recipeEntity.getCategoryId());
        List<IngredientEntity> ingredientEntities = ingredientRepository.findIngredientsOfRecipe(recipeId);
        List<StepEntity> stepEntities = stepRepository.findStepsOfRecipe(recipeId);

        return recipeMapper.map(recipeEntity, ingredientEntities, stepEntities, userEntity, categoryEntity);
    }
}
