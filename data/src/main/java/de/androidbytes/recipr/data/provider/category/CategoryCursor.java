package de.androidbytes.recipr.data.provider.category;

import android.database.Cursor;
import android.support.annotation.Nullable;
import de.androidbytes.recipr.data.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code category} table.
 */
public class CategoryCursor extends AbstractCursor implements CategoryModel {
    public CategoryCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(CategoryColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code user_id} value.
     */
    public long getUserId() {
        Long res = getLongOrNull(CategoryColumns.USER_ID);
        if (res == null)
            throw new NullPointerException("The value of 'user_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Name of the CategoryEntity.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(CategoryColumns.NAME);
        return res;
    }
}
