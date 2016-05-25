package de.androidbytes.recipr.data.provider.user;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import de.androidbytes.recipr.data.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code user} table.
 */
public class UserContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return UserColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable UserSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable UserSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Google Id. Provided by the Authorization Service like Google.
     */
    public UserContentValues putGoogleId(@Nullable String value) {
        mContentValues.put(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public UserContentValues putGoogleIdNull() {
        mContentValues.putNull(UserColumns.GOOGLE_ID);
        return this;
    }

    /**
     * name of the user account.
     */
    public UserContentValues putName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("name must not be null");
        mContentValues.put(UserColumns.NAME, value);
        return this;
    }


    /**
     * Url to an image source used as a profile picture of the specific user account.
     */
    public UserContentValues putImageUrl(@Nullable String value) {
        mContentValues.put(UserColumns.IMAGE_URL, value);
        return this;
    }

    public UserContentValues putImageUrlNull() {
        mContentValues.putNull(UserColumns.IMAGE_URL);
        return this;
    }

    public UserContentValues putLoggedIn(@NonNull Boolean value) {
        if (value == null) throw new IllegalArgumentException("loggedIn must not be null");
        mContentValues.put(UserColumns.LOGGED_IN, value);
        return this;
    }
}
