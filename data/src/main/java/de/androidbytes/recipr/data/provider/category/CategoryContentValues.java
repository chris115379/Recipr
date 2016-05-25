package de.androidbytes.recipr.data.provider.category;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import de.androidbytes.recipr.data.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code category} table.
 */
public class CategoryContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CategoryColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable CategorySelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable CategorySelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public CategoryContentValues putUserId(long value) {
        mContentValues.put(CategoryColumns.USER_ID, value);
        return this;
    }

    /**
     * Name of the CategoryEntity.
     */
    public CategoryContentValues putName(@Nullable String value) {
        mContentValues.put(CategoryColumns.NAME, value);
        return this;
    }

    public CategoryContentValues putNameNull() {
        mContentValues.putNull(CategoryColumns.NAME);
        return this;
    }
}
