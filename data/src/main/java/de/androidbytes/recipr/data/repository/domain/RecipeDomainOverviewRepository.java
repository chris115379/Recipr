package de.androidbytes.recipr.data.repository.domain;

import de.androidbytes.recipr.data.entity.*;
import de.androidbytes.recipr.data.entity.mapper.CategoryMapper;
import de.androidbytes.recipr.data.entity.mapper.RecipeMapper;
import de.androidbytes.recipr.data.repository.data.*;
import de.androidbytes.recipr.domain.model.Category;
import de.androidbytes.recipr.domain.model.CategoryRecipeAggregate;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.RecipeOverviewRepository;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RecipeDomainOverviewRepository implements RecipeOverviewRepository {

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
    CategoryMapper categoryMapper;


    @Inject
    public RecipeDomainOverviewRepository() {
    }

    @Override
    public Observable<List<CategoryRecipeAggregate>> findAllCategories(long userId, int recipesPerCategoryCount) {

        UserEntity userEntity = userRepository.findById(userId);
        List<CategoryEntity> categories = categoryRepository.findAllOfUser(userId);

        List<CategoryRecipeAggregate> categoryRecipeAggregates = new ArrayList<>();

        for (CategoryEntity categoryEntity : categories) {
            Category category = categoryMapper.map(categoryEntity, userEntity);

            List<RecipeEntity> recipesInCategory = recipeRepository.findRecipesOfUserAndCategory(userId, categoryEntity.getId(), recipesPerCategoryCount);

            List<Recipe> recipes = new ArrayList<>(recipesInCategory.size());
            for (RecipeEntity recipeEntity : recipesInCategory) {
                recipes.add(getRecipeFromEntity(userEntity, categoryEntity, recipeEntity));
            }

            categoryRecipeAggregates.add(new CategoryRecipeAggregate(category, recipes));
        }

        return Observable.just(categoryRecipeAggregates);
    }

    @Override
    public Observable<List<Recipe>> findAllFavorites(long userId) {

        UserEntity userEntity = userRepository.findById(userId);
        List<Recipe> recipes = new ArrayList<>();

        List<RecipeEntity> recipesInCategory = recipeRepository.findFavoriteRecipesOfUser(userId);

        for (RecipeEntity recipeEntity : recipesInCategory) {
            CategoryEntity categoryEntity = categoryRepository.findCategoryById(userId, recipeEntity.getCategoryId());
            recipes.add(getRecipeFromEntity(userEntity, categoryEntity, recipeEntity));
        }

        return Observable.just(recipes);
    }

    @Override
    public Observable<List<Recipe>> getRecipesOfCategory(long userId, String categoryName) {

        CategoryEntity categoryEntity = categoryRepository.findByUserIdAndName(userId, categoryName);
        UserEntity userEntity = userRepository.findById(userId);

        List<Recipe> recipes = new ArrayList<>();
        List<RecipeEntity> recipesInCategory = recipeRepository.findRecipesOfUserAndCategory(userId, categoryEntity.getId(), -1);

        for (RecipeEntity recipeEntity : recipesInCategory) {
            recipes.add(getRecipeFromEntity(userEntity, categoryEntity, recipeEntity));
        }

        return Observable.just(recipes);
    }

    @Override
    public List<Recipe> findRecipesOfUserByName(long userId, String searchRecipes) {

        UserEntity userEntity = userRepository.findById(userId);
        List<Recipe> recipes = new ArrayList<>();

        List<RecipeEntity> recipesOfUserByName = recipeRepository.findRecipesOfUserByName(userId, searchRecipes);

        for (RecipeEntity recipeEntity : recipesOfUserByName) {
            CategoryEntity categoryEntity = categoryRepository.findCategoryById(userId, recipeEntity.getCategoryId());
            recipes.add(getRecipeFromEntity(userEntity, categoryEntity, recipeEntity));
        }

        return recipes;
    }

    private Recipe getRecipeFromEntity(UserEntity userEntity, CategoryEntity categoryEntity, RecipeEntity recipeEntity) {
        long recipeId = recipeEntity.getId();
        List<IngredientEntity> ingredientEntities = ingredientRepository.findIngredientsOfRecipe(recipeId);
        List<StepEntity> stepEntities = stepRepository.findStepsOfRecipe(recipeId);

        return recipeMapper.map(recipeEntity, ingredientEntities, stepEntities, userEntity, categoryEntity);
    }
}
