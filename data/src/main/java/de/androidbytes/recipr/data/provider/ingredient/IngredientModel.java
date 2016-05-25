package de.androidbytes.recipr.data.provider.ingredient;

import android.support.annotation.Nullable;
import de.androidbytes.recipr.data.provider.base.BaseModel;

/**
 * A recipe is an Entity used to combine related ingredients and steps for preparation.
 */
public interface IngredientModel extends BaseModel {

    /**
     * Get the {@code recipe_id} value.
     */
    long getRecipeId();

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
    String getQuantity();

    /**
     * Rating of the recipe.
     * Can be {@code null}.
     */
    @Nullable
    String getUnit();
}
