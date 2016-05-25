package de.androidbytes.recipr.data.provider.user;

import android.net.Uri;
import android.provider.BaseColumns;
import de.androidbytes.recipr.data.provider.ReciprProvider;


/**
 * A user is an account assigned to the physical human using Plannr.
 */
public class UserColumns implements BaseColumns {
    public static final String TABLE_NAME = "user";
    public static final Uri CONTENT_URI = Uri.parse(ReciprProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Google Id. Provided by the Authorization Service like Google.
     */
    public static final String GOOGLE_ID = "user__google_id";

    /**
     * name of the user account.
     */
    public static final String NAME = "user__name";

    /**
     * Url to an image source used as a profile picture of the specific user account.
     */
    public static final String IMAGE_URL = "user__image_url";

    public static final String LOGGED_IN = "user__logged_in";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            GOOGLE_ID,
            NAME,
            IMAGE_URL,
            LOGGED_IN
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(GOOGLE_ID) || c.contains("." + GOOGLE_ID)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(IMAGE_URL) || c.contains("." + IMAGE_URL)) return true;
            if (c.equals(LOGGED_IN) || c.contains("." + LOGGED_IN)) return true;
        }
        return false;
    }

}
