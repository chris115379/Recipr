package de.androidbytes.recipr.data.provider.user;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import de.androidbytes.recipr.data.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code user} table.
 */
public class UserCursor extends AbstractCursor implements UserModel {
    public UserCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(UserColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Google Id. Provided by the Authorization Service like Google.
     * Can be {@code null}.
     */
    @Nullable
    public String getGoogleId() {
        String res = getStringOrNull(UserColumns.GOOGLE_ID);
        return res;
    }

    /**
     * name of the user account.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getName() {
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
    public String getImageUrl() {
        String res = getStringOrNull(UserColumns.IMAGE_URL);
        return res;
    }

    @NonNull
    public Boolean isLoggedIn() {
        Boolean res = getBooleanOrNull(UserColumns.LOGGED_IN);
        if (res == null)
            throw new NullPointerException("The value of 'loggedIn' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
