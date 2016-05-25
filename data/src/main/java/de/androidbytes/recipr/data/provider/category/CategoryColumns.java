package de.androidbytes.recipr.data.provider.category;

import android.net.Uri;
import android.provider.BaseColumns;

import de.androidbytes.recipr.data.provider.ReciprProvider;
import de.androidbytes.recipr.data.provider.user.UserColumns;

/**
 * A category is used to group various favoriteRecipes.
 */
public class CategoryColumns implements BaseColumns {
    public static final String TABLE_NAME = "category";
    public static final Uri CONTENT_URI = Uri.parse(ReciprProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String USER_ID = "category__user_id";

    /**
     * Name of the CategoryEntity.
     */
    public static final String NAME = "category__name";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            USER_ID,
            NAME
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(USER_ID) || c.contains("." + USER_ID)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
        }
        return false;
    }

    public static final String PREFIX_USER = TABLE_NAME + "__" + UserColumns.TABLE_NAME;
}
