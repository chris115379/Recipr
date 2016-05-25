package de.androidbytes.recipr.data.provider.recipe;

import android.net.Uri;
import android.provider.BaseColumns;
import de.androidbytes.recipr.data.provider.ReciprProvider;
import de.androidbytes.recipr.data.provider.category.CategoryColumns;
import de.androidbytes.recipr.data.provider.user.UserColumns;


/**
 * A recipe is an Entity used to combine related ingredients and steps for preparation.
 */
public class RecipeColumns implements BaseColumns {
    public static final String TABLE_NAME = "recipe";
    public static final Uri CONTENT_URI = Uri.parse(ReciprProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String USER_ID = "recipe__user_id";

    public static final String CATEGORY_ID = "recipe__category_id";

    /**
     * Name of the recipe.
     */
    public static final String NAME = "recipe__name";

    /**
     * Rating of the recipe.
     */
    public static final String FAVORITE = "recipe__favorite";

    /**
     * Image Url of the recipe.
     */
    public static final String IMAGE_URL = "recipe__image_url";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            USER_ID,
            CATEGORY_ID,
            NAME,
            FAVORITE,
            IMAGE_URL
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(USER_ID) || c.contains("." + USER_ID)) return true;
            if (c.equals(CATEGORY_ID) || c.contains("." + CATEGORY_ID)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(FAVORITE) || c.contains("." + FAVORITE)) return true;
            if (c.equals(IMAGE_URL) || c.contains("." + IMAGE_URL)) return true;
        }
        return false;
    }

    public static final String PREFIX_USER = TABLE_NAME + "__" + UserColumns.TABLE_NAME;
    public static final String PREFIX_CATEGORY = TABLE_NAME + "__" + CategoryColumns.TABLE_NAME;
}
