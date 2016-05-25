package de.androidbytes.recipr.data.repository.domain;

import android.content.ContentResolver;
import de.androidbytes.recipr.data.entity.*;
import de.androidbytes.recipr.data.exception.DatabaseException;
import de.androidbytes.recipr.data.provider.category.CategoryCursor;
import de.androidbytes.recipr.data.provider.category.CategorySelection;
import de.androidbytes.recipr.data.provider.ingredient.IngredientCursor;
import de.androidbytes.recipr.data.provider.ingredient.IngredientSelection;
import de.androidbytes.recipr.data.provider.recipe.RecipeCursor;
import de.androidbytes.recipr.data.provider.recipe.RecipeSelection;
import de.androidbytes.recipr.data.provider.step.StepCursor;
import de.androidbytes.recipr.data.provider.step.StepSelection;
import de.androidbytes.recipr.data.provider.user.UserCursor;
import de.androidbytes.recipr.data.provider.user.UserSelection;
import de.androidbytes.recipr.domain.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeDomainWidgetRepository {

    private ContentResolver contentResolver;

    public RecipeDomainWidgetRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public List<Recipe> findAllRecipes() {

        HashMap<Long, UserEntity> userEntities = new HashMap<>();

        List<RecipeEntity> recipesInCategory = getAllRecipes();
        List<Recipe> recipes = new ArrayList<>(recipesInCategory.size());

        for (RecipeEntity recipeEntity : recipesInCategory) {
            long userId = recipeEntity.getUserId();
            UserEntity userEntity;
            if (userEntities.containsKey(userId))
                userEntity = userEntities.get(userId);
            else
                userEntity = getUserById(userId);

            CategoryEntity categoryEntity = getCategoryEntity(userId, recipeEntity.getCategoryId());


            long recipeId = recipeEntity.getId();
            List<IngredientEntity> ingredientEntities = getIngredientsOfRecipe(recipeId);
            List<StepEntity> stepEntities = getStepsOfRecipe(recipeId);


            final List<Ingredient> ingredients = new ArrayList<>(ingredientEntities.size());
            for (IngredientEntity ingredientEntity : ingredientEntities) {
                ingredients.add(new Ingredient(
                        ingredientEntity.getId(),
                        ingredientEntity.getName(),
                        ingredientEntity.getQuantity(),
                        ingredientEntity.getUnit()
                ));
            }

            final List<Step> steps = new ArrayList<>(stepEntities.size());
            for (StepEntity stepEntity : stepEntities) {
                steps.add(new Step(
                        stepEntity.getId(),
                        stepEntity.getNumber(),
                        stepEntity.getInstruction()
                ));
            }

            User user = new User(
                    userEntity.getId(),
                    userEntity.getGoogleId(),
                    userEntity.getName(),
                    userEntity.getImageUrl(),
                    userEntity.isLoggedIn()
            );
            recipes.add(new Recipe(
                    recipeEntity.getId(),
                    recipeEntity.getName(),
                    ingredients,
                    steps,
                    recipeEntity.isFavorite(),
                    recipeEntity.getImageUrl(),
                    user,
                    new Category(
                            categoryEntity.getId(),
                            user,
                            categoryEntity.getName()
                    )
            ));
        }

        return recipes;
    }

    private List<StepEntity> getStepsOfRecipe(long recipeId) {
        StepSelection stepSelection = new StepSelection();
        stepSelection.recipeId(recipeId);
        StepCursor stepCursor = stepSelection.query(contentResolver);

        List<StepEntity> stepEntities = new ArrayList<>(stepCursor.getCount());
        while (stepCursor.moveToNext()) {
            stepEntities.add(new StepEntity(stepCursor));
        }

        return stepEntities;
    }

    private List<IngredientEntity> getIngredientsOfRecipe(long recipeId) {
        IngredientSelection ingredientSelection = new IngredientSelection();
        ingredientSelection.recipeId(recipeId);
        IngredientCursor ingredientCursor = ingredientSelection.query(contentResolver);

        List<IngredientEntity> ingredientEntities = new ArrayList<>(ingredientCursor.getCount());
        while (ingredientCursor.moveToNext()) {
            ingredientEntities.add(new IngredientEntity(ingredientCursor));
        }

        return ingredientEntities;
    }

    private CategoryEntity getCategoryEntity(long userId, long categoryId) {
        CategorySelection categorySelection = new CategorySelection();
        categorySelection.id(categoryId).and().userId(userId);
        CategoryCursor categoryCursor = categorySelection.query(contentResolver);
        if (categoryCursor.moveToFirst()) {
            return new CategoryEntity(categoryCursor);
        } else {
            throw new DatabaseException("A category with the id of '" + categoryId + "' was not found for the current user.");
        }
    }

    private UserEntity getUserById(long userId) {
        UserSelection userSelection = new UserSelection();
        userSelection.id(userId);
        UserCursor userCursor = userSelection.query(contentResolver);

        if (userCursor.moveToFirst()) {
            return new UserEntity(userCursor);
        }

        throw new DatabaseException("No user with id '" + userId + "' found.");
    }

    private List<RecipeEntity> getAllRecipes() {
        RecipeSelection recipeSelection = new RecipeSelection();
        RecipeCursor recipeCursor = recipeSelection.query(contentResolver);

        List<RecipeEntity> recipeEntities = new ArrayList<>(recipeCursor.getCount());
        while (recipeCursor.moveToNext()) {
            recipeEntities.add(new RecipeEntity(recipeCursor));
        }

        return recipeEntities;
    }
}
