package de.androidbytes.recipr.data.provider.step;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import de.androidbytes.recipr.data.provider.base.AbstractCursor;
import de.androidbytes.recipr.data.provider.category.CategoryColumns;
import de.androidbytes.recipr.data.provider.recipe.RecipeColumns;
import de.androidbytes.recipr.data.provider.user.UserColumns;

/**
 * Cursor wrapper for the {@code step} table.
 */
public class StepCursor extends AbstractCursor implements StepModel {
    public StepCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(StepColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code recipe_id} value.
     */
    public long getRecipeId() {
        Long res = getLongOrNull(StepColumns.RECIPE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'recipe_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code user_id} value.
     */
    public long getRecipeUserId() {
        Long res = getLongOrNull(RecipeColumns.USER_ID);
        if (res == null)
            throw new NullPointerException("The value of 'user_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Google Id. Provided by the Authorization Service like Google.
     * Can be {@code null}.
     */
    @Nullable
    public String getRecipeUserGoogleId() {
        String res = getStringOrNull(UserColumns.GOOGLE_ID);
        return res;
    }

    /**
     * name of the user account.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getRecipeUserName() {
        String res = getStringOrNull(UserColumns.NAME);
        if (res == null)
            throw new NullPointerException("The value of 'name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Url to an image source used as a profile picture of the specific user account.
     * Can be {@code null}.
     */
    @Nullable
    public String getRecipeUserImageUrl() {
        String res = getStringOrNull(UserColumns.IMAGE_URL);
        return res;
    }

    /**
     * Get the {@code category_id} value.
     */
    public long getRecipeCategoryId() {
        Long res = getLongOrNull(RecipeColumns.CATEGORY_ID);
        if (res == null)
            throw new NullPointerException("The value of 'category_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Name of the CategoryEntity.
     * Can be {@code null}.
     */
    @Nullable
    public String getRecipeCategoryName() {
        String res = getStringOrNull(CategoryColumns.NAME);
        return res;
    }

    /**
     * Name of the recipe.
     * Can be {@code null}.
     */
    @Nullable
    public String getRecipeName() {
        String res = getStringOrNull(RecipeColumns.NAME);
        return res;
    }

    /**
     * Rating of the recipe.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getRecipeRating() {
        Integer res = getIntegerOrNull(RecipeColumns.FAVORITE);
        return res;
    }

    /**
     * Image Url of the recipe.
     * Can be {@code null}.
     */
    @Nullable
    public String getRecipeImageUrl() {
        String res = getStringOrNull(RecipeColumns.IMAGE_URL);
        return res;
    }

    /**
     * Title of the StepEntity.
     * Can be {@code null}.
     */
    @Nullable
    public int getNumber() {
        int res = getIntegerOrNull(StepColumns.NUMBER);
        return res;
    }

    /**
     * Instruction of the preparation step.
     * Can be {@code null}.
     */
    @Nullable
    public String getInstruction() {
        String res = getStringOrNull(StepColumns.INSTRUCTION);
        return res;
    }
}
