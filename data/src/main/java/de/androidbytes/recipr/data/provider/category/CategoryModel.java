package de.androidbytes.recipr.data.provider.category;

import de.androidbytes.recipr.data.provider.base.BaseModel;

import android.support.annotation.Nullable;

/**
 * A category is used to group various favoriteRecipes.
 */
public interface CategoryModel extends BaseModel {

    /**
     * Get the {@code user_id} value.
     */
    long getUserId();

    /**
     * Name of the CategoryEntity.
     * Can be {@code null}.
     */
    @Nullable
    String getName();
}
