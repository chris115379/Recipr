package de.androidbytes.recipr.data.repository.data;

import android.content.ContentResolver;
import android.net.Uri;
import de.androidbytes.recipr.data.entity.IngredientEntity;
import de.androidbytes.recipr.data.exception.DatabaseException;
import de.androidbytes.recipr.data.provider.ingredient.IngredientColumns;
import de.androidbytes.recipr.data.provider.ingredient.IngredientContentValues;
import de.androidbytes.recipr.data.provider.ingredient.IngredientCursor;
import de.androidbytes.recipr.data.provider.ingredient.IngredientSelection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class IngredientDataRepository {

    private ContentResolver contentResolver;

    @Inject
    public IngredientDataRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public IngredientEntity createIngredient(IngredientEntity ingredientEntity) {

        IngredientContentValues contentValues = ingredientEntity.getContentValues();
        Uri createdIngredientUri = contentResolver.insert(IngredientColumns.CONTENT_URI, contentValues.values());
        if (createdIngredientUri != null) {
            IngredientCursor ingredientCursor = new IngredientCursor(contentResolver.query(createdIngredientUri, null, null, null, null));
            if (ingredientCursor.moveToFirst())
                return new IngredientEntity(ingredientCursor);
        }

        throw new DatabaseException("Could not create ingredient with name '" + ingredientEntity.getName() + "'");

    }

    public List<IngredientEntity> findIngredientsOfRecipe(long recipeId) {
        IngredientSelection ingredientSelection = new IngredientSelection();
        ingredientSelection.recipeId(recipeId);
        IngredientCursor ingredientCursor = ingredientSelection.query(contentResolver);

        List<IngredientEntity> ingredientEntities = new ArrayList<>(ingredientCursor.getCount());
        while (ingredientCursor.moveToNext()) {
            ingredientEntities.add(new IngredientEntity(ingredientCursor));
        }

        return ingredientEntities;
    }
}
