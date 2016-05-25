package de.androidbytes.recipr.data.provider.recipe;

import android.support.annotation.Nullable;
import de.androidbytes.recipr.data.provider.base.BaseModel;

/**
 * A recipe is an Entity used to combine related ingredients and steps for preparation.
 */
public interface RecipeModel extends BaseModel {

    /**
     * Get the {@code user_id} value.
     */
    long getUserId();

    /**
     * Get the {@code category_id} value.
     */
    long getCategoryId();

    /**
     * Name of the recipe.
     * Can be {@code null}.
     */
    @Nullable
    String getName();

    /**
     * Rating of the recipe.
     * Can be {@code null}.
     */
    @Nullable
    Boolean isFavorite();

    /**
     * Image Url of the recipe.
     * Can be {@code null}.
     */
    @Nullable
    String getImageUrl();
}
