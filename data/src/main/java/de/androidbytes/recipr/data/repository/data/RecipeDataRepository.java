package de.androidbytes.recipr.data.repository.data;

import android.content.ContentResolver;
import android.net.Uri;
import de.androidbytes.recipr.data.entity.RecipeEntity;
import de.androidbytes.recipr.data.exception.DatabaseException;
import de.androidbytes.recipr.data.provider.recipe.RecipeColumns;
import de.androidbytes.recipr.data.provider.recipe.RecipeContentValues;
import de.androidbytes.recipr.data.provider.recipe.RecipeCursor;
import de.androidbytes.recipr.data.provider.recipe.RecipeSelection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class RecipeDataRepository {

    private ContentResolver contentResolver;

    @Inject
    public RecipeDataRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public RecipeEntity createRecipe(RecipeEntity recipeEntity) {

        RecipeContentValues contentValues = recipeEntity.getContentValues();
        Uri createdRecipeUri = contentResolver.insert(RecipeColumns.CONTENT_URI, contentValues.values());
        if (createdRecipeUri != null) {
            long createdRecipeId = Long.parseLong(createdRecipeUri.getLastPathSegment());
            return findRecipeOfUserById(recipeEntity.getUserId(), createdRecipeId);
        }

        throw new DatabaseException("Could not create ingredient with name '" + recipeEntity.getName() + "'");

    }

    public List<RecipeEntity> findRecipesOfUserAndCategory(long userId, long categoryId, int recipeLimit) {
        RecipeSelection recipeSelection = new RecipeSelection();
        recipeSelection.userId(userId).and().categoryId(categoryId);
        if (recipeLimit != -1)
            recipeSelection.limit(recipeLimit);

        RecipeCursor recipeCursor = recipeSelection.query(contentResolver);
        List<RecipeEntity> recipeEntities = new ArrayList<>(recipeCursor.getCount());
        while (recipeCursor.moveToNext()) {
            recipeEntities.add(new RecipeEntity(recipeCursor));
        }

        return recipeEntities;
    }

    public RecipeEntity findRecipeOfUserById(long userId, long recipeId) {

        RecipeSelection recipeSelection = new RecipeSelection();
        recipeSelection.userId(userId).and().id(recipeId);
        RecipeCursor recipeCursor = recipeSelection.query(contentResolver);

        if (recipeCursor.moveToFirst() && recipeCursor.getCount() == 1) {
            return new RecipeEntity(recipeCursor);
        }

        throw new DatabaseException("No recipe with id '" + recipeId + "' found.");

    }

    public List<RecipeEntity> findFavoriteRecipesOfUser(long userId) {
        RecipeSelection recipeSelection = new RecipeSelection();
        recipeSelection.userId(userId).and().favorite(true);
        RecipeCursor recipeCursor = recipeSelection.query(contentResolver);

        List<RecipeEntity> recipeEntities = new ArrayList<>(recipeCursor.getCount());
        while (recipeCursor.moveToNext()) {
            recipeEntities.add(new RecipeEntity(recipeCursor));
        }

        return recipeEntities;
    }

    public List<RecipeEntity> findRecipesOfUserByName(long userId, String searchRecipes) {
        RecipeSelection recipeSelection = new RecipeSelection();
        recipeSelection.userId(userId).and().nameLike("%" + searchRecipes + "%");
        RecipeCursor recipeCursor = recipeSelection.query(contentResolver);

        List<RecipeEntity> recipeEntities = new ArrayList<>(recipeCursor.getCount());
        while (recipeCursor.moveToNext()) {
            recipeEntities.add(new RecipeEntity(recipeCursor));
        }

        return recipeEntities;
    }

    public RecipeEntity changeFavorStateOfRecipe(long userId, long recipeId, boolean favorite) {

        RecipeEntity recipeEntity = findRecipeOfUserById(userId, recipeId);

        if (favorite != recipeEntity.isFavorite()) {
            RecipeContentValues contentValues = recipeEntity.getContentValues();
            contentValues.putFavorite(favorite);
            RecipeSelection recipeSelection = new RecipeSelection();
            recipeSelection.id(recipeId).and().userId(userId);
            contentValues.update(contentResolver, recipeSelection);

            return findRecipeOfUserById(userId, recipeId);
        } else {
            return recipeEntity;
        }
    }
}
