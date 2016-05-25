package de.androidbytes.recipr.data.provider.step;

import android.net.Uri;
import android.provider.BaseColumns;
import de.androidbytes.recipr.data.provider.ReciprProvider;
import de.androidbytes.recipr.data.provider.recipe.RecipeColumns;


/**
 * A recipe is an Entity used to combine related ingredients and steps for preparation.
 */
public class StepColumns implements BaseColumns {
    public static final String TABLE_NAME = "step";
    public static final Uri CONTENT_URI = Uri.parse(ReciprProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String RECIPE_ID = "step__recipe_id";

    /**
     * Title of the StepEntity.
     */
    public static final String NUMBER = "step__number";

    /**
     * Instruction of the preparation step.
     */
    public static final String INSTRUCTION = "step__instruction";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            RECIPE_ID,
            NUMBER,
            INSTRUCTION
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(RECIPE_ID) || c.contains("." + RECIPE_ID)) return true;
            if (c.equals(NUMBER) || c.contains("." + NUMBER)) return true;
            if (c.equals(INSTRUCTION) || c.contains("." + INSTRUCTION)) return true;
        }
        return false;
    }

    public static final String PREFIX_RECIPE = TABLE_NAME + "__" + RecipeColumns.TABLE_NAME;
}
